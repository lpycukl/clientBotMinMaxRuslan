package clientresponse;

public class SurrenderResponse implements Response {
    public String command = "SURRENDER";
    private final String status = "success";
    public String message;

    public SurrenderResponse(String message) {
        this.message = message;
    }
}
