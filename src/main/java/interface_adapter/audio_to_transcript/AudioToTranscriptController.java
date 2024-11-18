package interface_adapter.audio_to_transcript;

import use_case.audioToTranscript.AudioToTranscriptInputBoundary;
import use_case.audioToTranscript.AudioToTranscriptInputData;

import java.io.File;

public class AudioToTranscriptController {

    private final AudioToTranscriptInputBoundary audioToTranscriptInteractor;

    public AudioToTranscriptController (AudioToTranscriptInputBoundary
                                                audioToTranscriptInteractor) {
        this.audioToTranscriptInteractor = audioToTranscriptInteractor;
    }

    public void execute (File audiofile, String model, String language) {
        final AudioToTranscriptInputData inputData = new AudioToTranscriptInputData(audiofile, model, language);
        audioToTranscriptInteractor.execute(inputData);
    }
}
