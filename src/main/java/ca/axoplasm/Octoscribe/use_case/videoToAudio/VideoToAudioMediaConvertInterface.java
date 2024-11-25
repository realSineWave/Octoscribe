package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import java.io.File;

public interface VideoToAudioMediaConvertInterface {

    String audioToVideo (File file);

    String getFileName ();
}
