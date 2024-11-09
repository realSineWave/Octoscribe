package use_case.translateTranscript;

public class translateTranscriptOutputData {
    private final String fileName;

    private final boolean status;

    public translateTranscriptOutputData(String fileName, Boolean status){
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
