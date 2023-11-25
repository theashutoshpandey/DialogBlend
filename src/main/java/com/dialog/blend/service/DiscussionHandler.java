package com.dialog.blend.service;

import java.util.ArrayList;

import com.dialog.blend.dto.QueryConversation;
import com.dialog.blend.logX.LogOperator;
import com.dialog.blend.logX.Logger;
import com.dialog.blend.model.Conversation;
import com.dialog.blend.util.NumberToGuess;
import com.dialog.blend.util.YesNoDetector;
import com.google.gson.Gson;

import core_nlp.SentimentAnalyzer.SentimentAnalyzer;
import core_nlp.model.SentimentResult;

public class DiscussionHandler {

    private int totalAttempt = 1;
    private int questionIndex = 0;
    private Conversation conversation;
    private ArrayList<Conversation> conversations;

    private Gson gson;

    private SentimentAnalyzer sentimentAnalyzer;
    private SentimentResult sentimentResult;

    private YesNoDetector yesNoDetector;
    private String numberToGuess;

    private Logger logger;

    public DiscussionHandler() {

        this.logger = LogOperator.getLogger();
        this.conversations = new ConversationRuleMapper().getConversations();

        this.gson = new Gson();

        this.sentimentAnalyzer = new SentimentAnalyzer();
        sentimentAnalyzer.initialize();

        this.yesNoDetector = new YesNoDetector();

        this.numberToGuess = new NumberToGuess().getGuessNum();
        System.out.println("Number to guess : " + numberToGuess);
    }

    public String createResponse(QueryConversation userQuery) {
        this.conversation = conversations.get(questionIndex);
        this.sentimentResult = sentimentAnalyzer.getSentimentResult(userQuery.getMessage());
        String sentiment = sentimentResult.getSentimentType();
        logger.info(userQuery.getSessionId() + "|Expected Input : " + conversation.getExpectedAnswer()
                + "| User Input : " + userQuery.getMessage());
        logger.info(userQuery.getSessionId() + "|Fetched sentiment : " + sentiment);

        int stage = conversation.getMatchCase().ordinal();
        logger.info(userQuery.getSessionId() + "|Process Stage : " + stage);

        if (stage == 0) {
            questionIndex++;
            return gson.toJson(userQuery.setResponse(conversations.get(questionIndex).getBotQuestion()));

        } else if (stage == 1) { // String
            if (questionIndex == 4) { // end state
                return gson
                        .toJson(userQuery.setHangup(1).setResponse(conversations.get(questionIndex).getBotQuestion()));
            } else if (checkResultContains(conversation.getExpectedAnswer(), sentiment, userQuery.getMessage())) {
                questionIndex++;
                return gson.toJson(userQuery.setResponse(conversations.get(questionIndex).getBotQuestion()));
            } else {
                return gson.toJson(userQuery.setResponse(conversation.getBotQuestion()));
            }

        } else if (stage == 2) { // number

            if (checkNumResultContains(numberToGuess, userQuery.getMessage())) {
                questionIndex++;
                logger.info(userQuery.getSessionId() + "| Winner Number to guess : " + numberToGuess);
                return gson
                        .toJson(userQuery.setHangup(1).setResponse(conversations.get(questionIndex).getBotQuestion()));
            } else if (totalAttempt == 1) {
                questionIndex += stage;
                totalAttempt++;
                return gson.toJson(userQuery.setResponse(conversations.get(questionIndex).getBotQuestion()));
            } else {
                return gson.toJson(userQuery.setHangup(1).setResponse(
                        "Thanks for playing. Despite the outcome, I appreciate your participation. Once again, Happy New Year! Take Care"));
            }

        } else if (stage == 3) { // NLP

            if (questionIndex == 2) { // if user not wanted to play
                if (checkResultContains(conversation.getExpectedAnswer(), sentiment, userQuery.getMessage())) {
                    questionIndex++;
                    return gson.toJson(userQuery.setResponse(conversations.get(questionIndex).getBotQuestion()));
                } else {
                    return gson.toJson(userQuery.setHangup(1).setResponse(
                            "Oh, sorry to hear that. Thanks for your time once again. Happy New Year! Take care."));
                }
            } else if (questionIndex == 5) { // to try one more attempt

                if (checkResultContains(conversation.getExpectedAnswer(), sentiment, userQuery.getMessage())) {
                    questionIndex = 3;
                    return gson.toJson(userQuery.setResponse(conversations.get(questionIndex).getBotQuestion()));
                } else {
                    return gson.toJson(userQuery.setHangup(1)
                            .setResponse("Thanks for your time once again. Happy New Year! Take care.")); // attempt max
                }

            } else if (checkResultContains(conversation.getExpectedAnswer(), sentiment, userQuery.getMessage())) {
                questionIndex++;
                return gson.toJson(userQuery.setResponse(conversations.get(questionIndex).getBotQuestion()));
            } else {
                return gson.toJson(userQuery.setResponse(conversation.getBotQuestion()));
            }

        } else
            return "No Context Found!!";

    }

    private boolean checkResultContains(String expectedAnswer, String sentiment, String userQuery) {
        return expectedAnswer.contains(sentiment.toLowerCase())
                && !yesNoDetector.detectResponse(userQuery).equals("No");
    }

    private boolean checkNumResultContains(String expectedAnswer, String sentiment) {

        String[] words = sentiment.split("\\s+");

        for (String word : words) {
            if (expectedAnswer.contains(word)) {
                return true;
            }
        }
        return false;
    }

}
