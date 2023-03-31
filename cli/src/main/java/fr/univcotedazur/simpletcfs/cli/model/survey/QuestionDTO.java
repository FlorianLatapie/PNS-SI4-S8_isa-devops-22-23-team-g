package fr.univcotedazur.simpletcfs.cli.model.survey;

public record QuestionDTO (Long id, String question, String type, String[] answers) {
}
