package ca.axoplasm.Octoscribe.use_case.videoToAudio;

public class VideoToAudioInteractor implements VideoToAudioInputBoundary {
    private final VideoToAudioMediaConvertInterface mci;

    public VideoToAudioInteractor(VideoToAudioMediaConvertInterface mci) {
        this.mci = mci;
    }

    @Override
    public VideoToAudioOutputData execute(VideoToAudioInputData data) {
        String status = mci.audioToVideo(data.getVideoFile());

        if (!status.equals("Video Conversion Successful")){
            return new VideoToAudioOutputData(null, true);
        } else {
            return new VideoToAudioOutputData(mci.getFileName(), false);
        }
    }
}
