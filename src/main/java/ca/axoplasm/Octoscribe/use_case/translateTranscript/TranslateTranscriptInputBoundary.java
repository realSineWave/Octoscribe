package ca.axoplasm.Octoscribe.use_case.translateTranscript;

public interface TranslateTranscriptInputBoundary {
    /**
     * Execute translateTranscript use case.
     * @param translateTranscriptInputData input data for audioToTranscript use case.
     */
    void execute(TranslateTranscriptInputData translateTranscriptInputData);
}
