package clientrequest;

public class AuthorizationRequest implements Request {
    public final String command = "AUTHORIZATION";
    public String nickname;
    public AuthorizationRequest(String nickname) {
        this.nickname = nickname;
    }
}
