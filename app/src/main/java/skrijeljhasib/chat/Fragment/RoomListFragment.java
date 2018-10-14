package skrijeljhasib.chat.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Client.RoomClient;
import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.Fragment.Adapter.RoomsAdapter;
import skrijeljhasib.chat.Helper.JsonObjectConverter;
import skrijeljhasib.chat.R;

public class RoomListFragment extends Fragment {

    public RoomClient roomClient;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        roomClient = ((ChatApplication) getActivity().getApplication()).getRoomClient();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        try {
            fetchRooms(view);
            socketListener();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void socketListener() {
        Socket socket = ((ChatApplication) getActivity().getApplication()).getSocket();

        socket.on(Socket.EVENT_CONNECT, onSocketConnectListener);

        socket.on("chat.room.create", onRoomCreateListener);

        socket.connect();
    }

    public Emitter.Listener onSocketConnectListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            System.out.println("WebSocket joined");
        }
    };

    public Emitter.Listener onRoomCreateListener = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        fetchRooms(getView());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Room created");
                }
            });
        }
    };

    public void fetchRooms (View view) throws JSONException {
        String data = roomClient.fetchRooms();

        if (!data.isEmpty()) {

            JSONArray dataArray = new JSONObject(data).getJSONArray("data");

            ArrayList<Room> roomsArray = new ArrayList<>();

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject json = dataArray.getJSONObject(i);
                // Fix this issue
                json.remove("contexts");
                json.remove("messages");
                json.remove("created_at");
                String roomJson = json.toString();
                System.out.println(roomJson);
                roomsArray.add((Room) JsonObjectConverter.jsonToObject(roomJson, Room.class));
            }

            RoomsAdapter roomsAdapter = new RoomsAdapter(getActivity(), roomsArray);

            ListView roomListFound = view.findViewById(R.id.rooms_found_list);

            roomListFound.setAdapter(roomsAdapter);
        }
    }
}
