package skrijeljhasib.chat.Client;

import java.util.HashMap;
import java.util.Map;

import skrijeljhasib.chat.Entity.Entity;
import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.Helper.JsonObjectConverter;

public class RoomClient extends ApiClient
{
    public RoomClient(String url, String authorization)
    {
        super(url + "/api/rooms", authorization);
    }

    public String createRoom(Room room)
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("room", room);

        return this.post(JsonObjectConverter.objectToJson(parameters));
    }

    public String fetchRoom(String id)
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        return this.get(parameters);
    }
}
