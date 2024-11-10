package use_case.translateTranscript;

public class TranslateTranscriptOutputData {
    private final String fileName;

    private final boolean status;

    public TranslateTranscriptOutputData(String fileName, Boolean status){
        this.fileName = fileName;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public Boolean getStatus(){
        return this.status;
    }
}
