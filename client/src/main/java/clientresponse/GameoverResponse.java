package clientresponse;

public class GameoverResponse implements Response {
    protected final String command = "GAMEOVER";
    public int quantityOfGame;
    public String status;
    public String message;
    public boolean roomCreator;

    public GameoverResponse(String status, String message, int quantityOfGame, boolean roomCreator) {
        this.status = status;
        this.message = message;
        this.quantityOfGame = quantityOfGame;
        this.roomCreator = roomCreator;
    }

    public String getStatus() {

        return status;
    }
}
