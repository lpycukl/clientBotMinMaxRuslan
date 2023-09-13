package clientresponse;

public class MakeMoveResponse implements Response {
    public String command = "MAKEMOVE";

    public String status;
    public String message;

    public MakeMoveResponse(String status, String message) {
        this.message = message;
        this.status = status;
    }


}
