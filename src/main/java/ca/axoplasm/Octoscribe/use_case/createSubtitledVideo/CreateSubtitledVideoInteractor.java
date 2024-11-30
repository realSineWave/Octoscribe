package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioOutputData;

public class CreateSubtitledVideoInteractor implements CreateSubtitledVideoInputBoundary{
    private final CreateSubtitledVideoMediaConvertInterface mci;
    private final CreateSubtitledVideoOutputBoundary userpresenter;

    public CreateSubtitledVideoInteractor(CreateSubtitledVideoMediaConvertInterface mci, CreateSubtitledVideoOutputBoundary ob) {
        this.mci = mci;
        this.userpresenter = ob;
    }

    @Override
    public void execute(CreateSubtitledVideoInputData data) {
        String status = mci.createSubtitledVideo(data.getVideoFile(), data.getSubtitleFile());
        if (!status.equals("Video Conversion Successful")){
            userpresenter.prepareFailureView(status);
        } else {
            final CreateSubtitledVideoOutputData output = new CreateSubtitledVideoOutputData(mci.getFileName(), false);
            userpresenter.prepareSuccessView(output);
        }
    }
}
