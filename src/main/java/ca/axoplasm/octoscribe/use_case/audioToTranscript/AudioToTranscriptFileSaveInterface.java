package ca.axoplasm.octoscribe.use_case.audioToTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.nio.file.Path;

public interface AudioToTranscriptFileSaveInterface {

void save (SegmentedTranscription segmentedTranscription, Path path);

    String getName();
}
