package skrijeljhasib.chat.Client;

import skrijeljhasib.chat.Entity.Room;

public class RoomClient extends ApiClient {
    public RoomClient(String url, String authorization) {
        super(url + "/api/rooms", authorization);
    }

    public String joinRoom(String username, Room room) {

        String urlParams = "/" + room.getId() + "/join" ;

        String jsonBody = "username=" + username;

        return post(urlParams, jsonBody);
    }

    public String fetchRooms() {
        return this.get("");
    }
}
