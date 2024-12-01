package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

/**
 * Input Boundary Interface for audioToTranscript use case.
 */

public interface AudioToTranscriptInputBoundary {

    /**
     * Execute the audioToTranscript use case.
     * @param audioToTranscriptInputData input data for audioToTranscript use case.
     */
    AudioToTranscriptOutputData execute(AudioToTranscriptInputData audioToTranscriptInputData);
}
