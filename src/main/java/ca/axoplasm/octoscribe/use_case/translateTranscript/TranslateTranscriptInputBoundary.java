package ca.axoplasm.octoscribe.use_case.translateTranscript;

import java.io.IOException;

public interface TranslateTranscriptInputBoundary {
    /**
     * Execute translateTranscript use case.
     * @param translateTranscriptInputData input data for audioToTranscript use case.
     */
    TranslateTranscriptOutputData execute(TranslateTranscriptInputData translateTranscriptInputData) throws IOException;
}
