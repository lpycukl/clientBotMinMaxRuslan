package clientrequest;

public class MakeMoveRequest implements Request{

    public String command = "MAKEMOVE";

    public int row;
    public int col;

    public MakeMoveRequest(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
