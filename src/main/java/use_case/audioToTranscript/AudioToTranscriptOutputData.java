package use_case.audioToTranscript;

public class audioToTranscriptOutputData {

    private final String fileName;

    private final boolean status;

    public audioToTranscriptOutputData(String fileName, Boolean status){
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
