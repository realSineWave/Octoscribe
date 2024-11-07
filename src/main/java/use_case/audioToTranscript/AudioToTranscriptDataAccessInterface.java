package use_case.audioToTranscript;

/**
 * The interface of the DAO for the Transcribe Use Case.
 */
public interface AudioToTranscriptDataAccessInterface {

    /**
     * save the new file.
     */
    void save();

    /**
     * Checkout the original language
     */
    void checkOriginLanguage();

    /**
     * Checkout target language
     */
    void checkTargetLanguage();
}
