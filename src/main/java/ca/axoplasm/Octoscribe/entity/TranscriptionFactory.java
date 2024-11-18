package ca.axoplasm.Octoscribe.entity;

public class TranscriptionFactory implements TransFactory{

    @Override
    public Transcription createTranscription(String language, String text) {
        return new Transcription(language, text);
    }
}
