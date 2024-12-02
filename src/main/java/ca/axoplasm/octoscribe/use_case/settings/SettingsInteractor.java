package ca.axoplasm.octoscribe.use_case.settings;

import ca.axoplasm.octoscribe.entity.Settings;

import java.io.IOException;

public class SettingsInteractor {
    SettingsDataAccessInterface settingsDataAccessObject;

    public SettingsInteractor(SettingsDataAccessInterface settingsDataAccessObject) {
        this.settingsDataAccessObject = settingsDataAccessObject;
    }

    public Settings updateSettings(String whisperAPIEndpoint,
                                   String whisperAPIKey,
                                   String deeplAPIKey) throws IOException {
        Settings settings = new Settings(whisperAPIEndpoint, whisperAPIKey, deeplAPIKey);
        settingsDataAccessObject.saveSettings(settings);

        return settings;
    }

    public Settings loadSettings() {
        return settingsDataAccessObject.loadSettings();
    }
}
