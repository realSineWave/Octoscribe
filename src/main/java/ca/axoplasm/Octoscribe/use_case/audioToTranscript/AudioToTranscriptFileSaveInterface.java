package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.nio.file.Path;

public interface AudioToTranscriptFileSaveInterface {

void save (SegmentedTranscription segmentedTranscription, Path path);

    String getName();
}
