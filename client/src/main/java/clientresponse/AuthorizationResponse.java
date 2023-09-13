package clientresponse;

public class AuthorizationResponse implements Response {
    public final String command = "AUTHORIZATION";
    public String message;
    public String status;

    public AuthorizationResponse(String status) {
        this.status = status;
    }
}
