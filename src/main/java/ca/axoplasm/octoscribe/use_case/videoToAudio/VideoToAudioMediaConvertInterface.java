package ca.axoplasm.octoscribe.use_case.videoToAudio;

import java.io.File;

public interface VideoToAudioMediaConvertInterface {

    String videoToAudio(File file);

    File getFile();
}