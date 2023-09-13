package clientrequest;

public class RegistrationRequest implements Request {
    public final String command = "REGISTRATION";
    public String nickname;

    public RegistrationRequest(String nickname) {
        this.nickname = nickname;
    }
}
