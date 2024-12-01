package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import java.io.File;

public class CreateSubtitledVideoInteractor implements CreateSubtitledVideoInputBoundary {
    private final CreateSubtitledVideoMediaConvertInterface mediaConvertInterface;

    public CreateSubtitledVideoInteractor(CreateSubtitledVideoMediaConvertInterface mci) {
        this.mediaConvertInterface = mci;
    }

    @Override
    public CreateSubtitledVideoOutputData execute(CreateSubtitledVideoInputData data) {
        String status = mediaConvertInterface.createSubtitledVideo(data.getVideoFile(), data.getSubtitleFile());

        if (!status.equals("Video Conversion Successful")) {
            return new CreateSubtitledVideoOutputData(null, true);
        } else {
            return new CreateSubtitledVideoOutputData(mediaConvertInterface.getFile(), false);
        }
    }
}
