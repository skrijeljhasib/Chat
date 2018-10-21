package skrijeljhasib.chat.Fragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.R;

public class RoomsAdapter extends ArrayAdapter<Room> {

    private ArrayList roomList;
    private ChatApplication chatApplication;

    public RoomsAdapter(Context context, ArrayList<Room> r, ChatApplication cA) {
        super(context, 0, r);
        roomList = r;
        chatApplication = cA;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Room room = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_room, parent, false);
        }

        TextView roomName = convertView.findViewById(R.id.room_name);
        ImageButton roomJoinButton = convertView.findViewById(R.id.room_join_button);

        roomName.setText(room.getName());

        roomJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatApplication.getRoomClient().joinRoom(chatApplication.getUsername(), room);
                roomList.remove(room);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}