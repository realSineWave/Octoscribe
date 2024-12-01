package ca.axoplasm.Octoscribe.use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;

public class TranslateTranscriptInteractor implements TranslateTranscriptInputBoundary{
    private final TranslateTranscriptDataAccessInterface dao;
    private final TranslateTranscriptFileSaveObject saveObject;

    public TranslateTranscriptInteractor(TranslateTranscriptDataAccessInterface dao,
                                         TranslateTranscriptFileSaveObject saveObject) {
        this.dao = dao;
        this.saveObject = saveObject;
    }


    @Override
    public TranslateTranscriptOutputData execute(TranslateTranscriptInputData translateTranscriptInputData) {
        final SegmentedTranscription transcript = this.dao.TransSegmentedTranscription(
                translateTranscriptInputData.getSegmentedTranscription(),
                translateTranscriptInputData.getTargetLanguage());
        this.saveObject.save(transcript);
        return new TranslateTranscriptOutputData(transcript,
                this.saveObject.getName(), true);
    }
}
