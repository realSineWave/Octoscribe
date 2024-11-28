package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

public class CreateSubtitledVideoInteractor implements CreateSubtitledVideoInputBoundary{
    private final CreateSubtitledVideoMediaConvertInterface mediaConvertInterface;
    private final CreateSubtitledVideoOutputBoundary userPresenter;

    public CreateSubtitledVideoInteractor(CreateSubtitledVideoMediaConvertInterface mci, CreateSubtitledVideoOutputBoundary ob) {
        this.mediaConvertInterface = mci;
        this.userPresenter = ob;
    }

    @Override
    public void execute(CreateSubtitledVideoInputData data) {
        String status = mediaConvertInterface.createSubtitledVideo(data.getVideoFile(), data.getSubtitleFile());
        if (!status.equals("Video Conversion Successful")){
            userPresenter.prepareFailureView(status);
        } else {
            final CreateSubtitledVideoOutputData output = new CreateSubtitledVideoOutputData(mediaConvertInterface.getFileName(), false);
            userPresenter.prepareSuccessView(output);
        }
    }
}
