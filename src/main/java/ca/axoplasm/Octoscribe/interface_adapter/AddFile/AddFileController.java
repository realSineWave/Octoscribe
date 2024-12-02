package ca.axoplasm.Octoscribe.interface_adapter.AddFile;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptInputData;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptOutputData;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoInputBoundary;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoInputData;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoInteractor;
import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoOutputData;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFInputBoundary;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFInputData;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFOutputData;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputBoundary;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputData;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptOutputData;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioInputBoundary;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioInputData;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioOutputData;
import ca.axoplasm.Octoscribe.view.AddFileView;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AddFileController {
    List<FileState> fileStates;
    FileListModel fileListModel;
    AudioToTranscriptInputBoundary audioToTranscriptInteractor;
    TranslateTranscriptInputBoundary translateTranscriptInteractor;
    VideoToAudioInputBoundary videoToAudioInteractor;
    CreateSubtitledVideoInputBoundary createSubtitledVideoInteractor;
    TranscriptToPDFInputBoundary transcriptToPDFInteractor;
    Tika tika = new Tika();

    public AddFileController(List<FileState> fileStates,
                             FileListModel fileListModel,
                             AudioToTranscriptInputBoundary audioToTranscriptInteractor,
                             TranslateTranscriptInputBoundary translateTranscriptInteractor,
                             VideoToAudioInputBoundary videoToAudioInteractor,
                             CreateSubtitledVideoInputBoundary createSubtitledVideoInteractor,
                             TranscriptToPDFInputBoundary transcriptToPDFInteractor) {
        this.fileStates = fileStates;
        this.fileListModel = fileListModel;
        this.audioToTranscriptInteractor = audioToTranscriptInteractor;
        this.translateTranscriptInteractor = translateTranscriptInteractor;
        this.videoToAudioInteractor = videoToAudioInteractor;
        this.createSubtitledVideoInteractor = createSubtitledVideoInteractor;
        this.transcriptToPDFInteractor = transcriptToPDFInteractor;
    }

    public void addFiles(File[] files,
                         String modelName,
                         String audioLanguageCode,
                         boolean doTranslate,
                         String translateToLanguageCode,
                         boolean createSubVideo,
                         boolean createPDF) {
        for (File file : files) {
            FileOptions fileOptions = new FileOptions(modelName,
                    audioLanguageCode,
                    doTranslate,
                    translateToLanguageCode,
                    createSubVideo,
                    createPDF);
            FileState fileState = new FileState(file, fileOptions);
            fileStates.add(fileState);
        }

        fileListModel.fireTableDataChanged();
    }

    public void execute() throws IOException {
        for (FileState fileState : fileStates) {
            if (fileState.getStatus() == FileState.Status.PENDING) {
                File originalFile = fileState.getFile();
                File audioFile = originalFile;
                FileOptions fileOptions = fileState.getOptions();
                String fileType = tika.detect(originalFile);
                boolean fileIsVideo = fileType.split("/")[0].equals("video");

                // If file is a video, convert it to audio before moving forward
                if (fileIsVideo) {
                    VideoToAudioInputData videoToAudioInputData = new VideoToAudioInputData(originalFile);
                    VideoToAudioOutputData videoToAudioOutputData =
                            videoToAudioInteractor.execute(videoToAudioInputData);
                    audioFile = videoToAudioOutputData.getFile();
                // If file is neither an audio nor a video, bail out
                } else if (!fileType.split("/")[0].equals("audio")) {
                    fileState.setStatus(FileState.Status.FAILED);
                    fileListModel.fireTableDataChanged();
                    continue;
                }

                AudioToTranscriptInputData audioToTranscriptInputData =
                        new AudioToTranscriptInputData(
                                audioFile,
                                fileOptions.getModelName(),
                                fileOptions.getAudioLanguageCode());

                AudioToTranscriptOutputData outputData;
                try {
                    outputData = audioToTranscriptInteractor.execute(audioToTranscriptInputData);
                } catch (IOException ex) {
                    fileState.setStatus(FileState.Status.FAILED);
                    fileListModel.fireTableDataChanged();
                    continue;
                }

                SegmentedTranscription transcription = outputData.getSegmentedTranscription();

                TranslateTranscriptOutputData translateOutputData = null;
                // If translate is checked, next pipeline stages work on the translated transcription
                if (fileOptions.isDoTranslate()) {
                    TranslateTranscriptInputData translateTranscriptInputData =
                            new TranslateTranscriptInputData(
                                    transcription,
                                    fileOptions.getTranslateToLanguageCode(),
                                    originalFile
                            );

                    try {
                        translateOutputData = translateTranscriptInteractor.execute(translateTranscriptInputData);
                        transcription = translateOutputData.getSegmentedTranscription();
                    } catch (IOException e) {
                        fileState.setStatus(FileState.Status.FAILED);
                        fileListModel.fireTableDataChanged();
                        continue;
                    }
                }

                if (fileOptions.isCreatePDF()) {
                    TranscriptToPDFInputData transcriptToPDFInputData = new TranscriptToPDFInputData(transcription, originalFile);
                    TranscriptToPDFOutputData transcriptOutputData = transcriptToPDFInteractor.execute(transcriptToPDFInputData);
                }

                if (fileOptions.isCreateSubVideo()) {
                    if (!fileIsVideo) {
                        fileState.setStatus(FileState.Status.FAILED);
                        fileListModel.fireTableDataChanged();
                        continue;
                    }
                    CreateSubtitledVideoInputData createSubtitledVideoInputData;
                    if (fileOptions.isDoTranslate()) {
                         createSubtitledVideoInputData = new CreateSubtitledVideoInputData(originalFile, translateOutputData.getTranscript());
                    } else {
                         createSubtitledVideoInputData = new CreateSubtitledVideoInputData(originalFile, outputData.getTranscript());
                    }
                    try {
                        CreateSubtitledVideoOutputData createSubtitledVideoOutputData = createSubtitledVideoInteractor.execute(createSubtitledVideoInputData);
                    } catch (RuntimeException ex) {
                        fileState.setStatus(FileState.Status.FAILED);
                        fileListModel.fireTableDataChanged();
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
