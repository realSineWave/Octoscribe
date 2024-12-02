package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import java.io.File;

public interface VideoToAudioMediaConvertInterface {

    void videoToAudio(File file);

    File getFile();
}
