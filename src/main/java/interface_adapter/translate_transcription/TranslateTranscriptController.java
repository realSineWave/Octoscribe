package interface_adapter.translate_transcription;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import use_case.audioToTranscript.AudioToTranscriptInputData;
import use_case.translateTranscript.TranslateTranscriptInputBoundary;
import use_case.translateTranscript.TranslateTranscriptInputData;

import java.io.File;

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
