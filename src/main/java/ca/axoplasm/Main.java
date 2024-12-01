package ca.axoplasm;

import ca.axoplasm.Octoscribe.interface_adapter.AddFile.AddFileController;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileListModel;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileState;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptOutputBoundary;
import ca.axoplasm.Octoscribe.view.AddFileView;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<FileState> fileStates = new ArrayList<FileState>();
        FileListModel fileListModel = new FileListModel(fileStates);

        AddFileView addFileView = new AddFileView(fileListModel);
        AddFileController addFileController = new AddFileController(addFileView, fileStates, fileListModel);
        addFileView.setController(addFileController);
        addFileView.setVisible(true);
    }
}