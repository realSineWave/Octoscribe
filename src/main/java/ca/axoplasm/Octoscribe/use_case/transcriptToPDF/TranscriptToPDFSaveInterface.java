package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.io.File;
import java.nio.file.Path;

public interface TranscriptToPDFSaveInterface {

    File save(SegmentedTranscription segmentedTranscription, Path path);

}
