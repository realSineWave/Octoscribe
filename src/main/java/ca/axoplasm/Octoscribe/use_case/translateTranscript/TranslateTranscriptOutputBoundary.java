package use_case.translateTranscript;

public interface TranslateTranscriptOutputBoundary {

    void prepareSuccessView(TranslateTranscriptOutputData data);

    void prepareFailView(String errorMessage);

}
