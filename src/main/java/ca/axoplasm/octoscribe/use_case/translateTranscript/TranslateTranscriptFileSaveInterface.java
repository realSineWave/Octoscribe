package ca.axoplasm.octoscribe.use_case.translateTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.io.File;
import java.nio.file.Path;

public interface TranslateTranscriptFileSaveInterface {

    void save(SegmentedTranscription segmentedTranscription, Path path);

    String getName();

    File getTranscript();
}
