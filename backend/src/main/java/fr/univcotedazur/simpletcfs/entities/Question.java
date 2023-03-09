package fr.univcotedazur.simpletcfs.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Question {
    UUID id;
    String questionContent;
    List<Answer> answers;

    public Question(String questionContent){
        this.questionContent = questionContent;
        this.id = UUID.randomUUID();
        answers = new ArrayList<>();
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
