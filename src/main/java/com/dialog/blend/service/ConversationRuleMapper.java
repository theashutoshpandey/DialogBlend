package com.dialog.blend.service;

import com.dialog.blend.model.Conversation;
import com.dialog.blend.model.MatchCase;

import java.util.ArrayList;

public class ConversationRuleMapper {

        ArrayList<Conversation> conversations;

        public ConversationRuleMapper() {
                conversations = new ArrayList<>();

                conversations.add(new Conversation(
                                "",
                                "/root", MatchCase.NONE));

                conversations.add(new Conversation(
                                "Happy New Year, to you and your family from the DialogBlend team! A surprise gift is waiting for you—just make the right guess.",
                                "Positive,Very positive,Neutral", MatchCase.NLP));

                conversations.add(new Conversation(
                                "Want to play?",
                                "Positive,Very positive", MatchCase.NLP));

                conversations.add(new Conversation(
                                "Guess a number from zero to ten.",
                                "Number",
                                MatchCase.NUMBER));

                conversations.add(new Conversation(
                                "Wow! You have won! We will shortly send you the gift. Wishing you, again, a Very Happy New Year.",
                                "end", MatchCase.STRING));

                conversations.add(new Conversation(
                                "Ohh Sorry!! Want to play again?",
                                "yes,Positive,Very positive,neutral", MatchCase.NLP));

        }

        public ArrayList<Conversation> getConversations() {
                return new ArrayList<>(conversations);
        }
}
