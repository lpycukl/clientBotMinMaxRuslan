package clientresponse;

public class ConnectToRoomResponse implements Response {
    public String command = "CONNECTTOROOM";
    public String message;
    public String status;


    public ConnectToRoomResponse(String status, String message) {

        this.status = status;
        this.message = message;
    }

    public String getStatus() {

        return status;
    }
}
