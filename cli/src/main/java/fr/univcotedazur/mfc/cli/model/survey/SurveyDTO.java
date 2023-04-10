package fr.univcotedazur.mfc.cli.model.survey;

import java.util.List;

import fr.univcotedazur.mfc.cli.model.CliCustomer;

public record SurveyDTO (Long id, List<CliCustomer> participants, List<QuestionDTO> questions) {
}
