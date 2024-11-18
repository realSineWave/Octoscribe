package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

public interface AudioToTranscriptOutputBoundary {


    void prepareSuccessView(AudioToTranscriptOutputData data);

    void prepareFailView(String errorMessage);
}
