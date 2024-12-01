package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;

/**
 *
 */
public class AudioToTranscriptInteractor implements AudioToTranscriptInputBoundary{

    private final AudioToTranscriptDataAccessInterface dao;
    private final AudioToTranscriptFileSaveInterface saveObject;

    public AudioToTranscriptInteractor(AudioToTranscriptDataAccessInterface DataAccessObject, AudioToTranscriptFileSaveObject saveObject) {

        this.dao = DataAccessObject;
        this.saveObject = saveObject;

    }

    @Override
    public AudioToTranscriptOutputData execute(AudioToTranscriptInputData audioToTranscriptInputData) {

        final SegmentedTranscription transcript =
                dao.getSegmentedTranscription(audioToTranscriptInputData.getAudiofile());

        saveObject.save(transcript, audioToTranscriptInputData.getAudiofile().toPath());

        return new AudioToTranscriptOutputData(transcript, saveObject.getName(), true);
    }
}
