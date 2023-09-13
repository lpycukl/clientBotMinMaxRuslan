package clientresponse;


public class StartGameResponse implements Response{
    public final String command = "STARTGAME";

    public String status;
    public String message;

    public StartGameResponse(String status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

}