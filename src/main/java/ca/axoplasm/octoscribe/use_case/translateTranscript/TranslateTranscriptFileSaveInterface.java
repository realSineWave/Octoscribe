package ca.axoplasm.octoscribe.use_case.translateTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

public interface TranslateTranscriptFileSaveInterface {

    void save (SegmentedTranscription segmentedTranscription);

    String getName();
}
