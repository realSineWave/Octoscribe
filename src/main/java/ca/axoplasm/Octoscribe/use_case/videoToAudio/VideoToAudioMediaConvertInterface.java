package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import java.io.File;

public interface VideoToAudioMediaConvertInterface {

    void audioToVideo(File file);

    File getFile();
}
