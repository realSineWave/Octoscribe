package ca.axoplasm.Octoscribe.use_case.settings;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.entity.Settings;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.AddFileController;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInteractor;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInteractor;

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
