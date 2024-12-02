package ca.axoplasm.octoscribe.entity;

public class Settings {
    String whisperAPIEndpoint;
    String whisperAPIKey;
    String deeplAPIKey;

    public Settings(String whisperAPIEndpoint, String whisperAPIKey, String deeplAPIKey) {
        this.whisperAPIEndpoint = whisperAPIEndpoint;
        this.whisperAPIKey = whisperAPIKey;
        this.deeplAPIKey = deeplAPIKey;
    }

    public String getDeeplAPIKey() {
        return deeplAPIKey;
    }

    public String getWhisperAPIKey() {
        return whisperAPIKey;
    }

    public String getWhisperAPIEndpoint() {
        return whisperAPIEndpoint;
    }
}
