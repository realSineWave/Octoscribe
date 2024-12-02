package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import java.io.File;

public interface CreateSubtitledVideoMediaConvertInterface {

    void createSubtitledVideo(File video, File subtitle);

    File getFile();
}
