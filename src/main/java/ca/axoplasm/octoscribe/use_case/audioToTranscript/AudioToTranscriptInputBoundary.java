package ca.axoplasm.octoscribe.use_case.audioToTranscript;

import java.io.IOException;

/**
 * Input Boundary Interface for audioToTranscript use case.
 */

public interface AudioToTranscriptInputBoundary {

    /**
     * Execute the audioToTranscript use case.
     * @param audioToTranscriptInputData input data for audioToTranscript use case.
     */
    AudioToTranscriptOutputData execute(AudioToTranscriptInputData audioToTranscriptInputData) throws IOException;
}
