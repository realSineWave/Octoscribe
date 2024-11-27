package ca.axoplasm.Octoscribe.use_case.videoToAudio;

public class VideoToAudioInteractor implements VideoToAudioInputBoundary{
    private final VideoToAudioMediaConvertInterface mci;
    private final VideoToAudioOutputBoundary userpresenter;

    public VideoToAudioInteractor(VideoToAudioMediaConvertInterface mci, VideoToAudioOutputBoundary userpresenter) {
        this.mci = mci;
        this.userpresenter = userpresenter;
    }

    @Override
    public void execute(VideoToAudioInputData data) {
        String status = mci.audioToVideo(data.getVideoFile());
        if (!status.equals("Video Conversion Successful")){
            System.out.println(status);
            userpresenter.prepareFailureView(status);
        } else {
            final VideoToAudioOutputData output = new VideoToAudioOutputData(mci.getFileName(), false);
            userpresenter.prepareSuccessView(output);
        }
    }
}
