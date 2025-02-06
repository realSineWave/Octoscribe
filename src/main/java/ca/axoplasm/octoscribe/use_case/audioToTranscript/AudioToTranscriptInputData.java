package ca.axoplasm.octoscribe.use_case.audioToTranscript;

import java.io.File;

public class AudioToTranscriptInputData {

    private final File audiofile;
    private final String model;
    private final String language;

    public AudioToTranscriptInputData(File audiofile, String model, String language) {
        this.audiofile = audiofile;
        this.model = model;
        this.language = language;
    }

    File getAudiofile() {
        return audiofile;
    }

    String getModel() {
        return model;
    }

    String getLanguage() {
        return language;
    }
}
