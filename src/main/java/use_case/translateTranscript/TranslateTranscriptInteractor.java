package use_case.translateTranscript;

public class TranslateTranscriptInteractor {
    private final TranslateTranscriptDataAccessInterface dao;

    public TranslateTranscriptInteractor(TranslateTranscriptDataAccessInterface dao) {
        this.dao = dao;
    }
}
