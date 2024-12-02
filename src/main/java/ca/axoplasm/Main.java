package ca.axoplasm;

import ca.axoplasm.Octoscribe.data_access.MediaConvertObject;
import ca.axoplasm.Octoscribe.data_access.SettingsFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.TranscriptToPDFSaveObject;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.AddFileController;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileListModel;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileState;
import ca.axoplasm.Octoscribe.interface_adapter.Settings.SettingsController;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoInputBoundary;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoInteractor;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoMediaConvertInterface;
import ca.axoplasm.Octoscribe.use_case.settings.SettingsDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.settings.SettingsInteractor;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFInputBoundary;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFInteractor;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFSaveInterface;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioInputBoundary;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioInteractor;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioMediaConvertInterface;
import ca.axoplasm.Octoscribe.view.AboutView;
import ca.axoplasm.Octoscribe.view.AddFileView;
import ca.axoplasm.Octoscribe.view.SettingsView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<FileState> fileStates = new ArrayList<FileState>();
        FileListModel fileListModel = new FileListModel(fileStates);

        VideoToAudioMediaConvertInterface videoToAudioMediaConvertObject = new MediaConvertObject();
        VideoToAudioInputBoundary videoToAudioInteractor = new VideoToAudioInteractor(videoToAudioMediaConvertObject);

        CreateSubtitledVideoMediaConvertInterface createSubtitledVideoMediaConvertObject =
                (CreateSubtitledVideoMediaConvertInterface) videoToAudioMediaConvertObject;
        CreateSubtitledVideoInputBoundary createSubtitledVideoInteractor =
                new CreateSubtitledVideoInteractor(createSubtitledVideoMediaConvertObject);

        TranscriptToPDFSaveInterface transcriptToPDFSaveObject = new TranscriptToPDFSaveObject();
        TranscriptToPDFInputBoundary transcriptToPDFInteractor = new TranscriptToPDFInteractor(transcriptToPDFSaveObject);

        AddFileView addFileView = new AddFileView(fileListModel);
        AddFileController addFileController = new AddFileController(
                fileStates, fileListModel,
                videoToAudioInteractor,
                createSubtitledVideoInteractor,
                transcriptToPDFInteractor);
        addFileView.setController(addFileController);

        SettingsView settingsView = new SettingsView();
        SettingsDataAccessInterface settingsDataAccessObject;
        try {
            settingsDataAccessObject = new SettingsFileSaveObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SettingsInteractor settingsInteractor = new SettingsInteractor(settingsDataAccessObject);
        SettingsController settingsController = new SettingsController(settingsView, settingsInteractor);
        settingsController.setAddFileController(addFileController);
        settingsView.setController(settingsController);
        addFileView.setSettingsView(settingsView);

        AboutView aboutView = new AboutView();
        addFileView.setAboutView(aboutView);

        addFileView.setVisible(true);
    }
}