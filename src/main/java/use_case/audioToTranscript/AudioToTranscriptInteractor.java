package use_case.audioToTranscript;
import javax.json.JsonObject;
/**
 *
 */
public class AudioToTranscriptInteractor implements AudioToTranscriptInputBoundary{

    private final AudioToTranscriptDataAccessInterface DataAccessObject;
    private final AudioToTranscriptOutputBoundary OutputBoundary;

    public AudioToTranscriptInteractor(AudioToTranscriptDataAccessInterface DataAccessObject,
                                       AudioToTranscriptOutputBoundary OutputBoundary) {

        this.DataAccessObject = DataAccessObject;
        this.OutputBoundary = OutputBoundary;

    }

    @Override
    public void execute(AudioToTranscriptInputData audioToTranscriptInputData) {

        final JsonObject transcript = AudioToTranscriptDataAccessInterface.
                getTranscript(audioToTranscriptInputData.getAudiofile());

        AudioToTranscriptOutputData temp = new AudioToTranscriptOutputData(a, transcript);
        //Outputdata structure later need to be confirmedgigi

        AudioToTranscriptOutputBoundary.prepareSuccessView
                (AudioToTranscriptOutputData);
    }
}
