package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import java.io.File;

public class CreateSubtitledVideoInteractor implements CreateSubtitledVideoInputBoundary {
    private final CreateSubtitledVideoMediaConvertInterface mediaConvertInterface;

    public CreateSubtitledVideoInteractor(CreateSubtitledVideoMediaConvertInterface mci) {
        this.mediaConvertInterface = mci;
    }

    @Override
    public CreateSubtitledVideoOutputData execute(CreateSubtitledVideoInputData data) {
        mediaConvertInterface.createSubtitledVideo(data.getVideoFile(), data.getSubtitleFile());

        return new CreateSubtitledVideoOutputData(mediaConvertInterface.getFile(), false);
        }
    }
