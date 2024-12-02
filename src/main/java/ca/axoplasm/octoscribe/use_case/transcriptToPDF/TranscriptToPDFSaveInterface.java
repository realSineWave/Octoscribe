package ca.axoplasm.octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.io.File;
import java.nio.file.Path;

public interface TranscriptToPDFSaveInterface {

    File save(SegmentedTranscription segmentedTranscription, Path path);

}
