package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

public class CreateSubtitledVideoInteractor implements CreateSubtitledVideoInputBoundary {
    private final CreateSubtitledVideoMediaConvertInterface mediaConvertInterface;

    public CreateSubtitledVideoInteractor(CreateSubtitledVideoMediaConvertInterface mci) {
        this.mediaConvertInterface = mci;
    }

    @Override
    public CreateSubtitledVideoOutputData execute(CreateSubtitledVideoInputData data) {
        String status = mediaConvertInterface.createSubtitledVideo(data.getVideoFile(), data.getSubtitleFile());

        if (!status.equals("Video Conversion Successful")) {
            return new CreateSubtitledVideoOutputData(mediaConvertInterface.getFileName(), false);
        } else {
            return new CreateSubtitledVideoOutputData(null, true);
        }
    }
}
