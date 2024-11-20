package ca.axoplasm.Octoscribe.interface_adapter.translate_transcription;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputData;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputData;

public class TranslateTranscriptController {
    private final TranslateTranscriptInputBoundary translateTranscriptInteractor;

    public TranslateTranscriptController (TranslateTranscriptInputBoundary
                                                  translateTranscriptInteractor) {
        this.translateTranscriptInteractor = translateTranscriptInteractor;
    }

    public void execute (SegmentedTranscription segmentedTranscription, String language) {
        final TranslateTranscriptInputData inputData = new TranslateTranscriptInputData(segmentedTranscription,
                language);
        this.translateTranscriptInteractor.execute(inputData);
    }
}
