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
import skrijeljhasib.chat.Entity.Message;
import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.Fragment.Adapter.MessageAdapter;
import skrijeljhasib.chat.Helper.JsonObjectConverter;
import skrijeljhasib.chat.R;

public class RoomFragment extends Fragment {
    private Room room;
    private RecyclerView roomMessagesView;
    private ImageButton sendMessageButton;
    private TextInputEditText messageText;
    private ChatApplication chatApplication;
    private Socket socket;
    private RecyclerView.Adapter roomMessagesViewAdapter;
    private List<Message> messages = new ArrayList<>();
    private String username;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        roomMessagesViewAdapter = new MessageAdapter(context, messages);
        username = "Hasib";
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

        // To be removed
        room = new Room();
        room.setId(1);

        roomMessagesView = view.findViewById(R.id.room_messages);
        sendMessageButton = view.findViewById(R.id.send_message_button);
        messageText = view.findViewById(R.id.message_input);

        sendMessageButton.setOnClickListener(buttonListener);

        roomMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomMessagesView.setAdapter(roomMessagesViewAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    public void socketListener() {
        chatApplication = (ChatApplication) getActivity().getApplication();

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
                    JSONObject data = (JSONObject) args[0];

                    Message message;

                    try {
                        message = (Message) JsonObjectConverter.jsonToObject(data.get("message").toString(), Message.class);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    messages.add(message);
                    roomMessagesViewAdapter.notifyItemInserted(messages.size() - 1);
                    scrollToBottom();

                    System.out.println("Message received");
                }
            });
        }
    };

    public View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Message message = new Message();
            message.setBody(messageText.getText().toString());

            // Send to Chat-api here

            System.out.println(messageText.getText().toString());
        }
    };

    private void scrollToBottom() {
        roomMessagesView.scrollToPosition(roomMessagesViewAdapter.getItemCount() - 1);
    }
}