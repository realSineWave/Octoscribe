package ca.axoplasm.octoscribe.use_case.translateTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.io.IOException;

public class TranslateTranscriptInteractor implements TranslateTranscriptInputBoundary{
    private final TranslateTranscriptDataAccessInterface dao;
    private final TranslateTranscriptFileSaveInterface saveObject;

    public TranslateTranscriptInteractor(TranslateTranscriptDataAccessInterface dao,
                                         TranslateTranscriptFileSaveInterface saveObject) {
        this.dao = dao;
        this.saveObject = saveObject;
    }


    @Override
    public TranslateTranscriptOutputData execute(TranslateTranscriptInputData translateTranscriptInputData) throws IOException {
        final SegmentedTranscription transcript = this.dao.TransSegmentedTranscription(
                translateTranscriptInputData.getSegmentedTranscription(),
                translateTranscriptInputData.getTargetLanguage());
        this.saveObject.save(transcript);
        return new TranslateTranscriptOutputData(transcript,
                this.saveObject.getName(), true);
    }
}
