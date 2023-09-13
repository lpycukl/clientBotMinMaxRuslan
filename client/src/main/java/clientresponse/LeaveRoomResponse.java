package clientresponse;

public class LeaveRoomResponse implements Response {
    public String command = "LEAVEROOM";
    public String message;
    public String status;

    public LeaveRoomResponse(String status) {

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
