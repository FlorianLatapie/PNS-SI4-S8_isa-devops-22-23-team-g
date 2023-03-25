package fr.univcotedazur.simpletcfs.exceptions;

public class SurveyAlreadyExistsException extends Exception{
        public SurveyAlreadyExistsException(String message) {
            super(message);
        }

        public SurveyAlreadyExistsException() {
            super("The survey already exists");
        }
}
