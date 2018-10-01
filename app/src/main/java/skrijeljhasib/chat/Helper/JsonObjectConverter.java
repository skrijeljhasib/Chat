package skrijeljhasib.chat.Helper;

import com.google.gson.Gson;
import java.util.Map;

public class JsonObjectConverter
{
    public static String objectToJson(Map<String, Object> object)
    {
        Gson gson = new Gson();

        return gson.toJson(object);
    }

    public static Object jsonToObject(Class object, String json)
    {
        Gson gson = new Gson();

        return gson.fromJson(json, object);
    }
}
