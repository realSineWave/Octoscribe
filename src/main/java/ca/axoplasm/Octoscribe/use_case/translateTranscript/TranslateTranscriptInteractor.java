package ca.axoplasm.Octoscribe.use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;

public class TranslateTranscriptInteractor implements TranslateTranscriptInputBoundary{
    private final TranslateTranscriptDataAccessInterface dao;
    private final TranslateTranscriptFileSaveObject saveObject;
    private final TranslateTranscriptOutputBoundary outputBoundary;

    public TranslateTranscriptInteractor(TranslateTranscriptDataAccessInterface dao,
                                         TranslateTranscriptFileSaveObject saveObject,
                                         TranslateTranscriptOutputBoundary outputBoundary) {
        this.dao = dao;
        this.saveObject = saveObject;
        this.outputBoundary = outputBoundary;
    }


    @Override
    public void execute(TranslateTranscriptInputData translateTranscriptInputData) {
        final SegmentedTranscription transcript = this.dao.TransSegmentedTranscription(
                translateTranscriptInputData.getSegmentedTranscription(),
                translateTranscriptInputData.getTargetLanguage());
        this.saveObject.save(transcript);
        TranslateTranscriptOutputData temp = new TranslateTranscriptOutputData(transcript,
                this.saveObject.getName(), true);
        this.outputBoundary.prepareSuccessView(temp);
    }
}
