package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Question;

import java.util.List;

public class SurveyDTO {
    Long id;
    List<CustomerDTO> participants;
    List<Question> questions;

    public SurveyDTO(Long id, List<CustomerDTO> participants, List<Question> questions) {
        this.id = id;
        this.participants = participants;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "SurveyDTO{" +
                "id=" + id +
                ", participants=" + participants +
                ", questions=" + questions +
                '}';
    }
}
