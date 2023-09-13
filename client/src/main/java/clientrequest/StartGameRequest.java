package clientrequest;

public class StartGameRequest implements Request {
    public final String command = "STARTGAME";

    public int roomId;

    public StartGameRequest(int roomId) {
        this.roomId = roomId;
    }
}
