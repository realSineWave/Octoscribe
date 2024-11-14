package use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

public interface AudioToTranscriptFileSaveInterface {

void save (SegmentedTranscription segmentedTranscription);
}
