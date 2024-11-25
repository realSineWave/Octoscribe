package ca.axoplasm.Octoscribe.use_case.videoToAudio;

public interface VideoToAudioOutputBoundary {

    void prepareSuccessView(VideoToAudioOutputData data);

    void prepareFailureView(String errorMessage);
}
