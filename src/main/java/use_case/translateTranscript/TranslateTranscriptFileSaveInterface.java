package use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

public interface TranslateTranscriptFileSaveInterface {

    void save (SegmentedTranscription segmentedTranscription);

    String getName();
}
