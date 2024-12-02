package ca.axoplasm.Octoscribe.interface_adapter.Settings;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.entity.Settings;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.AddFileController;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInteractor;
import ca.axoplasm.Octoscribe.use_case.settings.SettingsInteractor;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInteractor;
import ca.axoplasm.Octoscribe.view.SettingsView;

import java.io.IOException;

public class SettingsController {
    private SettingsView settingsView;
    private SettingsInteractor settingsInteractor;
    private AddFileController addFileController;
    private Settings settings;

    public SettingsController(SettingsView view, SettingsInteractor settingsInteractor) {
        this.settingsView = view;
        this.settingsInteractor = settingsInteractor;
        loadSettings();
    }

    public void updateSettings(
            String whisperAPIEndpoint,
            String whisperAPIKey,
            String deeplAPIKey
    ) throws IOException {
        this.settings = settingsInteractor.updateSettings(whisperAPIEndpoint, whisperAPIKey, deeplAPIKey);
        updateAddFileController();
        updateView();
    }

    private void loadSettings() {
        this.settings = settingsInteractor.loadSettings();
        updateView();
    }

    private void updateView() {
        settingsView.setWhisperAPI(settings.getWhisperAPIEndpoint());
        settingsView.setWhisperAPIKey(settings.getWhisperAPIKey());
        settingsView.setDeeplAPIKey(settings.getDeeplAPIKey());
    }

    private void updateAddFileController() {
        AudioToTranscriptDataAccessInterface audioToTranscriptDataAccessObject =
                new DataAccessObject(settings.getWhisperAPIEndpoint(), settings.getWhisperAPIKey(), settings.getDeeplAPIKey());
        AudioToTranscriptFileSaveInterface audioToTranscriptFileSaveObject = new AudioToTranscriptFileSaveObject();
        AudioToTranscriptInputBoundary audioToTranscriptInteractor = new AudioToTranscriptInteractor(
                audioToTranscriptDataAccessObject, audioToTranscriptFileSaveObject);

        TranslateTranscriptDataAccessInterface translateTranscriptDataAccessObject =
                (TranslateTranscriptDataAccessInterface) audioToTranscriptDataAccessObject;
        TranslateTranscriptFileSaveInterface translateTranscriptFileSaveObject =
                new TranslateTranscriptFileSaveObject();
        TranslateTranscriptInputBoundary translateTranscriptInteractor =
                new TranslateTranscriptInteractor(
                        translateTranscriptDataAccessObject,
                        translateTranscriptFileSaveObject);

        addFileController.setAudioToTranscriptInteractor(audioToTranscriptInteractor);
        addFileController.setTranslateTranscriptInteractor(translateTranscriptInteractor);

    }

    public void setAddFileController(AddFileController addFileController) {
        this.addFileController = addFileController;
        updateAddFileController();
    }
}
