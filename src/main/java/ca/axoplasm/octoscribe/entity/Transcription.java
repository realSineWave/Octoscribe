package ca.axoplasm.octoscribe.entity;

import java.util.Locale;

public class Transcription {
    private final Locale language;
    private final String text;

    public Transcription(String language, String text) {
        this.language = new Locale.Builder().setLanguage(language).build();
        this.text = text;
    }

    public Locale getLanguageLocale() {
        return language;
    }

    public String getLanguageCode() {
        return language.getLanguage();
    }

    public String getText() {
        return text;
    }
}
