package use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.Transcription;

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

    /**
     * Output trans
     * @return Transcription
     */
    Transcription toTranscript();


}
