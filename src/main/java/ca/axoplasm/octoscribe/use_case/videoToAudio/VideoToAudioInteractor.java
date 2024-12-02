package ca.axoplasm.octoscribe.use_case.videoToAudio;

public class VideoToAudioInteractor implements VideoToAudioInputBoundary {
    private final VideoToAudioMediaConvertInterface mediaConvertObject;

    public VideoToAudioInteractor(VideoToAudioMediaConvertInterface mediaConvertObject) {
        this.mediaConvertObject = mediaConvertObject;
    }

    @Override
    public VideoToAudioOutputData execute(VideoToAudioInputData data) {
        mediaConvertObject.audioToVideo(data.getVideoFile());

        return new VideoToAudioOutputData(mediaConvertObject.getFile(), false);
    }
}




