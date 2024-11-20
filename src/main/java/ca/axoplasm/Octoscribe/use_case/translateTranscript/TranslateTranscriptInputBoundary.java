package use_case.translateTranscript;

import use_case.audioToTranscript.AudioToTranscriptInputData;

public interface TranslateTranscriptionInputBoundary {
    /**
     * Execute translateTranscript use case.
     * @param translateTranscriptInputData input data for audioToTranscript use case.
     */
    void execute(TranslateTranscriptInputData translateTranscriptInputData);
}
