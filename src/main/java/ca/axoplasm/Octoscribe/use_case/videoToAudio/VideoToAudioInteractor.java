package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import java.io.File;

public class VideoToAudioInteractor implements VideoToAudioInputBoundary {
    private final VideoToAudioMediaConvertInterface mci;

    public VideoToAudioInteractor(VideoToAudioMediaConvertInterface mci) {
        this.mci = mci;
    }

    @Override
    public VideoToAudioOutputData execute(VideoToAudioInputData data) {
        String output = mci.videoToAudio(data.getVideoFile());

        if (output.equals("Video Conversion Failed") || output.equals("System doesn't have FFMPEG")) {
            return new VideoToAudioOutputData(null, true);
        }
        else {
            return new VideoToAudioOutputData(new File(output), false);
        }
    }
}
