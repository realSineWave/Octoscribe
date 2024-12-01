package ca.axoplasm.Octoscribe.interface_adapter.AddFile;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputData;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptOutputData;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputData;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptOutputData;
import ca.axoplasm.Octoscribe.view.AddFileView;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AddFileController {
    AddFileView addFileView;
    List<FileState> fileStates;
    FileListModel fileListModel;
    AudioToTranscriptInputBoundary audioToTranscriptInteractor;
    TranslateTranscriptInputBoundary translateTranscriptInteractor;
    Tika tika = new Tika();

    public AddFileController(AddFileView addFileView,
                             List<FileState> fileStates,
                             FileListModel fileListModel,
                             AudioToTranscriptInputBoundary audioToTranscriptInteractor,
                             TranslateTranscriptInputBoundary translateTranscriptInteractor) {
        this.addFileView = addFileView;
        this.fileStates = fileStates;
        this.fileListModel = fileListModel;
        this.audioToTranscriptInteractor = audioToTranscriptInteractor;
        this.translateTranscriptInteractor = translateTranscriptInteractor;
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

    public void execute() throws IOException {
        for (FileState fileState : fileStates) {
            if (fileState.getStatus() == FileState.Status.PENDING) {
                File file = fileState.getFile();
                FileOptions fileOptions = fileState.getOptions();
                String fileType = tika.detect(file);

                // TODO: Handle different file types
                AudioToTranscriptInputData audioToTranscriptInputData =
                        new AudioToTranscriptInputData(
                                file,
                                fileOptions.getModelName(),
                                fileOptions.getAudioLanguageCode());

                AudioToTranscriptOutputData outputData;
                try {
                    outputData = audioToTranscriptInteractor.execute(audioToTranscriptInputData);
                } catch (IOException ex) {
                    fileState.setStatus(FileState.Status.FAILED);
                    continue;
                }

                SegmentedTranscription transcription = outputData.getSegmentedTranscription();

                if (fileOptions.isDoTranslate()) {
                    TranslateTranscriptInputData translateTranscriptInputData =
                            new TranslateTranscriptInputData(
                                    transcription,
                                    fileOptions.getTranslateToLanguageCode()
                            );
                    TranslateTranscriptOutputData translateOutputData;

                    try {
                        translateOutputData = translateTranscriptInteractor.execute(translateTranscriptInputData);
                        transcription = translateOutputData.getSegmentedTranscription();
                    } catch (IOException e) {
                        fileState.setStatus(FileState.Status.FAILED);
                        continue;
                    }
                }

                fileState.setStatus(FileState.Status.COMPLETE);
                fileListModel.fireTableDataChanged();
            }
        }
    }

    public void updateFileStatus(FileState fileState, FileState.Status status) {
        fileState.setStatus(status);
        fileListModel.fireTableDataChanged();
    }
}
