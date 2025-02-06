package ca.axoplasm.octoscribe.use_case.audioToTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.io.IOException;

/**
 *
 */
public class AudioToTranscriptInteractor implements AudioToTranscriptInputBoundary {

    private final AudioToTranscriptDataAccessInterface dao;
    private final AudioToTranscriptFileSaveInterface saveObject;

    public AudioToTranscriptInteractor(
            AudioToTranscriptDataAccessInterface DataAccessObject,
            AudioToTranscriptFileSaveInterface saveObject) {
        this.dao = DataAccessObject;
        this.saveObject = saveObject;
    }

    @Override
    public AudioToTranscriptOutputData execute(AudioToTranscriptInputData audioToTranscriptInputData) throws IOException {

        final SegmentedTranscription transcript =
                dao.getSegmentedTranscription(audioToTranscriptInputData.getAudiofile());

        saveObject.save(transcript, audioToTranscriptInputData.getAudiofile().toPath());

        return new AudioToTranscriptOutputData(transcript, saveObject.getName(), saveObject.getTranscript());

    }
}
