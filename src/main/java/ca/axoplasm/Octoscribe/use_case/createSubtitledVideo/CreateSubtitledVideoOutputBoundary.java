package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

public interface CreateSubtitledVideoOutputBoundary {

    void prepareSuccessView(CreateSubtitledVideoOutputData data);

    void prepareFailureView(String errormessage);
}
