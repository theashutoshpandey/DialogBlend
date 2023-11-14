package com.dialog.blend.model;

public class Conversation {

    private String botQuestion;
    private String expectedAnswer;
    private MatchCase matchCase;

    public Conversation(String botQuestion, String expectedAnswer, MatchCase matchCase) {
        this.botQuestion = botQuestion;
        this.expectedAnswer = expectedAnswer;
        this.matchCase = matchCase;
    }

    public String getBotQuestion() {
        return botQuestion;
    }

    public String getExpectedAnswer() {
        return expectedAnswer.toLowerCase();
    }

    public MatchCase getMatchCase() {
        return matchCase;
    }

}
