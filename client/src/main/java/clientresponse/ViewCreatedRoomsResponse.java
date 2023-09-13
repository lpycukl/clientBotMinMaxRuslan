package clientresponse;


public class ViewCreatedRoomsResponse implements Response {
        public final String command = "VIEWROOMS";

        public int roomId;

        public String status;

        public String message;

        public ViewCreatedRoomsResponse(String status, String message, int roomId) {
                this.message = message;
                this.roomId = roomId;
                this.status = status;
        }
}
