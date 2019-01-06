package skrijeljhasib.chat.Client;

import org.json.JSONException;
import org.json.JSONObject;

import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.Helper.JsonObjectConverter;

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

    public Room fetchRoom(String id) {

        Room room = new Room();

        try {
            JSONObject dataJsonObject= new JSONObject(this.get("/" + id));
            JSONObject roomJsonObject = (JSONObject) dataJsonObject.get("data");
            room = (Room) JsonObjectConverter.jsonToObject(roomJsonObject.toString(), Room.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return room;
    }
}
