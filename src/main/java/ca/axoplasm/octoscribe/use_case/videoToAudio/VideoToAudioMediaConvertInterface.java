package ca.axoplasm.octoscribe.use_case.videoToAudio;

import java.io.File;

public interface VideoToAudioMediaConvertInterface {

    void audioToVideo(File file);

    File getFile();
}
