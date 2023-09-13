package clientresponse;

public class CreateRoomResponse implements Response {

    public String command = "CREATEROOM";
    public String message;
    public String status;
    public Integer roomId;

    public CreateRoomResponse(String status, Integer roomId) {
        this.status = status;
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public int getRoomId() {
        return roomId;
    }

}
