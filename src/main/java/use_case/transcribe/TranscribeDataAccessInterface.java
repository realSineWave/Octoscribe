package use_case.transcribe;

/**
 * The interface of the DAO for the Transcribe Use Case.
 */
public interface TranscribeDataAccessInterface {

    /**
     * Updates the system to record a new transcription
     */
    void transcribe();
}
