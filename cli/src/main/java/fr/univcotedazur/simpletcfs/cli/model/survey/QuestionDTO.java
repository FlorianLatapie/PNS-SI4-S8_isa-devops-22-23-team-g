package fr.univcotedazur.simpletcfs.cli.model.survey;

import java.util.Arrays;
import java.util.Objects;

public record QuestionDTO (Long id, String question, String type, String[] answers) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDTO that = (QuestionDTO) o;
        return id == that.id 
            && Objects.equals(question, that.question) 
            && Objects.equals(type, that.type) 
            && Arrays.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, type, Arrays.hashCode(answers));
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id='" + id + '\'' +
                ", question=" + question +
                ", type=" + type +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }
}
