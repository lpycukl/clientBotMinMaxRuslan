package clientresponse;

public class GUIResponse implements Response{
    protected final String command = "GUI";

    public String status;
    public String message;

    public GUIResponse(String status, String message) {
        this.message = message;
        this.status = status;
    }
}
