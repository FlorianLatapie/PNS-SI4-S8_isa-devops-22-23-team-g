package fr.univcotedazur.simpletcfs.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Survey {
    UUID id;
    List<Customer> participants;
    List<Question> questions;


    public Survey() {
        this.id = UUID.randomUUID();
        this.participants = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Customer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Customer> participants) {
        this.participants = participants;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
