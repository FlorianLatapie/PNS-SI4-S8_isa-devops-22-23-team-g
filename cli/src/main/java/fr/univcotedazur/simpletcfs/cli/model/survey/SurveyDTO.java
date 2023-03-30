package fr.univcotedazur.simpletcfs.cli.model.survey;

import fr.univcotedazur.simpletcfs.cli.model.CliCustomer;

import java.util.List;

public record SurveyDTO (Long id, List<CliCustomer> participants, List<QuestionDTO> questions) {
}
