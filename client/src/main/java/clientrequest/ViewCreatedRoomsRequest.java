package clientrequest;

public class ViewCreatedRoomsRequest implements Request{
        public final String command = "VIEWROOMS";

        public int roomId;

        public ViewCreatedRoomsRequest(int roomId) {
                this.roomId = roomId;
        }
}
