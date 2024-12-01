package ca.axoplasm.Octoscribe.interface_adapter.AddFile;

import ca.axoplasm.Octoscribe.view.AddFileView;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddFileController {
    AddFileView addFileView;
    List<FileState> fileStates;
    FileListModel fileListModel;

    public AddFileController(AddFileView addFileView, List<FileState> fileStates, FileListModel fileListModel) {
        this.addFileView = addFileView;
        this.fileStates = fileStates;
        this.fileListModel = fileListModel;
    }

    public void addFiles(File[] files,
                         String modelName,
                         String audioLanguageCode,
                         boolean doTranslate,
                         String translateToLanguageCode,
                         boolean createSubVideo) {
        for (File file : files) {
            FileOptions fileOptions = new FileOptions(modelName,
                    audioLanguageCode,
                    doTranslate,
                    translateToLanguageCode,
                    createSubVideo);
            FileState fileState = new FileState(file, fileOptions);
            fileStates.add(fileState);
        }

        fileListModel.fireTableDataChanged();
    }

    public void execute() {

    }

    public void updateFileStatus(FileState fileState, FileState.Status status) {
        fileState.setStatus(status);
        updateUiFileStatus();
    }

    public void updateUiFileStatus() {
        JTable fileStatusTable = addFileView.getFileStatusTable();

    }
}
