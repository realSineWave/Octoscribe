package ca.axoplasm.octoscribe.use_case.createSubtitledVideo;

import java.io.File;

public interface CreateSubtitledVideoMediaConvertInterface {

    String createSubtitledVideo(File video, File subtitle);

    File getFile();
}
