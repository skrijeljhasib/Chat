package skrijeljhasib.chat.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Client.RoomClient;
import skrijeljhasib.chat.R;

public class RoomListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        ChatApplication chatApplication = (ChatApplication)getActivity().getApplication();

        RoomClient roomClient = chatApplication.getRoomClient();

        System.out.println(roomClient.fetchRooms());

        return view;
    }
}
