package use_case.audioToTranscript;

public interface AudioToTranscriptOutputBoundary {


    void prepareSuccessView(AudioToTranscriptOutputData data);

    void prepareFailView(String errorMessage);
}
