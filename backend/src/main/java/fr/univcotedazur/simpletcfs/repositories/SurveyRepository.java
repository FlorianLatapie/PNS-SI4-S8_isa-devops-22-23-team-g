package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.Survey;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SurveyRepository extends BasicRepositoryImpl<Survey, UUID> {}
