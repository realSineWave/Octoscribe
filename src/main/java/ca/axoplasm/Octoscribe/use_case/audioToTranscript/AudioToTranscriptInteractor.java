package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;

/**
 *
 */
public class AudioToTranscriptInteractor implements AudioToTranscriptInputBoundary{

    private final AudioToTranscriptDataAccessInterface dao;
    private final AudioToTranscriptFileSaveInterface saveObject;
    private final AudioToTranscriptOutputBoundary outputBoundary;

    public AudioToTranscriptInteractor(AudioToTranscriptDataAccessInterface DataAccessObject, AudioToTranscriptFileSaveObject saveObject,
                                       AudioToTranscriptOutputBoundary OutputBoundary) {

        this.dao = DataAccessObject;
        this.saveObject = saveObject;
        this.outputBoundary = OutputBoundary;

    }

    @Override
    public void execute(AudioToTranscriptInputData audioToTranscriptInputData) {

        final SegmentedTranscription transcript = dao.
                getSegmentedTranscription(audioToTranscriptInputData.getAudiofile());

        saveObject.save(transcript, audioToTranscriptInputData.getAudiofile().toPath());

        AudioToTranscriptOutputData temp =
                new AudioToTranscriptOutputData(transcript, saveObject.getName(), false);

        outputBoundary.prepareSuccessView(temp);
    }
}
