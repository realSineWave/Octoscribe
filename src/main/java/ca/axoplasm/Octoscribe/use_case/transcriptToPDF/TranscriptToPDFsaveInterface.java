package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.io.File;

public interface TranscriptToPDFsaveInterface {

    File save (SegmentedTranscription segmentedTranscription);

}
