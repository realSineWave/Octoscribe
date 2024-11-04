package use_case.transcribe;

/**
 * The interface of the DAO for the Transcribe Use Case.
 */
public interface TranscribeDataAccessInterface {

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
