package ca.axoplasm.Octoscribe.interface_adapter.AddFile;

public class FileOptions {
    private final String modelName;
    private final String audioLanguageCode;
    private final boolean doTranslate;
    private final String translateToLanguageCode;
    private final boolean createSubVideo;

    public FileOptions(String modelName,
                       String audioLanguageCode,
                       boolean doTranslate,
                       String translateToLanguageCode,
                       boolean createSubVideo) {
        this.modelName = modelName;
        this.audioLanguageCode = audioLanguageCode;
        this.doTranslate = doTranslate;
        this.translateToLanguageCode = translateToLanguageCode;
        this.createSubVideo = createSubVideo;
    }

    public String getModelName() {
        return modelName;
    }

    public String getAudioLanguageCode() {
        return audioLanguageCode;
    }

    public boolean isDoTranslate() {
        return doTranslate;
    }

    public String getTranslateToLanguageCode() {
        return translateToLanguageCode;
    }

    public boolean isCreateSubVideo() {
        return createSubVideo;
    }
}
