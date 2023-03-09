package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Question;

import java.util.List;
import java.util.UUID;

public class SurveyDTO {
    UUID id;
    List<CustomerDTO> participants;
    List<Question> questions;

    public SurveyDTO(UUID id, List<CustomerDTO> participants, List<Question> questions) {
        this.id = id;
        this.participants = participants;
        this.questions = questions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<CustomerDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<CustomerDTO> participants) {
        this.participants = participants;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
