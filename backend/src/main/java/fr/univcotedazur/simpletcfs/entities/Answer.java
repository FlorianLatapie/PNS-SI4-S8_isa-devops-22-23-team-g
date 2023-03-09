package fr.univcotedazur.simpletcfs.entities;

import java.util.UUID;

public class Answer {
    String answer;
    UUID questionRef;

    public Answer(String answer, UUID questionRef) {
        this.answer = answer;
        this.questionRef = questionRef;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public UUID getQuestion() {
        return questionRef;
    }
}
