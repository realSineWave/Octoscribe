package ca.axoplasm;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.AddFileController;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileListModel;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileState;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInteractor;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptDataAccessInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInteractor;
import ca.axoplasm.Octoscribe.view.AddFileView;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<FileState> fileStates = new ArrayList<FileState>();
        FileListModel fileListModel = new FileListModel(fileStates);

        AudioToTranscriptDataAccessInterface audioToTranscriptDataAccessObject = new DataAccessObject();
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

        AddFileView addFileView = new AddFileView(fileListModel);
        AddFileController addFileController = new AddFileController(
                addFileView, fileStates, fileListModel,
                audioToTranscriptInteractor,
                translateTranscriptInteractor);
        addFileView.setController(addFileController);
        addFileView.setVisible(true);
    }
}