package fr.univcotedazur.mfc.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


    @OneToMany()
    @JoinColumn(name = "survey_id")// cascade = CascadeType.REMOVE fetch = FetchType.EAGER : caused some problems
    // @OnDelete(action = OnDeleteAction.CASCADE)
    List<Customer> participants;

    @OneToMany(cascade = CascadeType.REMOVE) // cascade = CascadeType.REMOVE fetch = FetchType.EAGER : caused some problems
    // @OnDelete(action = OnDeleteAction.CASCADE)
    List<Question> questions;


    public Survey() {
        participants = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(questions, survey.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", participants=" + participants +
                ", questions=" + questions +
                '}';
    }
}
