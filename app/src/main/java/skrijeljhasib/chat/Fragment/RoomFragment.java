package skrijeljhasib.chat.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Client.MessageClient;
import skrijeljhasib.chat.Db.MessageProvider;
import skrijeljhasib.chat.Entity.Message;
import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.Fragment.Adapter.MessageAdapter;
import skrijeljhasib.chat.Helper.JsonObjectConverter;
import skrijeljhasib.chat.R;

public class RoomFragment extends Fragment {

    private Room room = new Room();
    private RecyclerView roomMessagesView;
    private TextInputEditText messageText;
    private ChatApplication chatApplication;
    private Socket socket;
    private RecyclerView.Adapter roomMessagesViewAdapter;
    private List<Message> messages = new ArrayList<>();
    private String username;
    private MessageClient messageClient;
    private MessageProvider messageProvider;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        chatApplication = (ChatApplication) getActivity().getApplication();
        messageProvider = chatApplication.getMessageProvider();
        roomMessagesViewAdapter = new MessageAdapter(context, messages);
        messageClient = chatApplication.getMessageClient();
        username = chatApplication.getUsername();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socketListener();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        room.setId(bundle.getInt("roomId"));

        roomMessagesView = view.findViewById(R.id.room_messages);
        ImageButton sendMessageButton = view.findViewById(R.id.send_message_button);
        messageText = view.findViewById(R.id.message_input);

        sendMessageButton.setOnClickListener(buttonListener);

        roomMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomMessagesView.setAdapter(roomMessagesViewAdapter);

        messages.clear();
        messages.addAll(messageProvider.getMessagesFromRoom(room.getId()));

        scrollToBottom();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    public void socketListener() {
        socket = chatApplication.getSocket();

        socket.on(Socket.EVENT_CONNECT, onSocketConnectListener);

        socket.on("chat.message.create", onMessageCreateListener);

        socket.connect();
    }

    public Emitter.Listener onSocketConnectListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            System.out.println("WebSocket joined");
        }
    };

    public Emitter.Listener onMessageCreateListener = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        JSONObject data = (JSONObject) args[0];

                        JSONObject messageJson = (JSONObject) data.get("message");
                        int roomId = (int) messageJson.get("room_id");

                        Message message = (Message) JsonObjectConverter.jsonToObject(messageJson.toString(), Message.class);

                        if (room.getId() == roomId) {
                            message.setRoom(room);
                            messages.add(message);
                            roomMessagesViewAdapter.notifyItemInserted(messages.size() - 1);
                            messageProvider.createMessage(message);
                            scrollToBottom();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!messageText.getText().toString().isEmpty()) {
                        Message message = new Message();
                        message.setUsername(username);
                        message.setBody(messageText.getText().toString());
                        message.setRoom(room);
                        messageClient.addMessageToRoom(message);
                        messageText.setText("");
                    }
                }
            });
        }
    };

    private void scrollToBottom() {
        roomMessagesView.scrollToPosition(roomMessagesViewAdapter.getItemCount() - 1);
    }
}