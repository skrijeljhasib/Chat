package skrijeljhasib.chat.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Entity.Room;
import skrijeljhasib.chat.Helper.JsonObjectConverter;
import skrijeljhasib.chat.R;

public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    ChatApplication chatApplication;
    DrawerLayout drawerLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        chatApplication = (ChatApplication) getActivity().getApplication();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = getActivity().findViewById(R.id.main_layout);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        Menu menuLayout = navigationView.getMenu();
        menuLayout.setGroupVisible(R.id.rooms_joined_list, true);

        try {
            showJoinedRooms(menuLayout);
        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }

        TextView roomListButton = headerLayout.findViewById(R.id.room_list_button);
        roomListButton.setOnClickListener(onRoomListClickListener);
    }

    void showJoinedRooms(Menu menu) throws JSONException {
        String data = chatApplication.getRoomClient().fetchRooms();

        if (!data.isEmpty()) {

            JSONArray dataArray = new JSONObject(data).getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject json = dataArray.getJSONObject(i);
                // Fix this issue
                json.remove("contexts");
                json.remove("messages");
                json.remove("created_at");
                String roomJson = json.toString();

                Room room = (Room) JsonObjectConverter.jsonToObject(roomJson, Room.class);

                menu.add(1, room.getId(), i, room.getName());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        RoomFragment roomFragment = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("roomId", item.getItemId());
        roomFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame_main, roomFragment)
                .addToBackStack(null)
                .commit();

        //item.setChecked(true);
        drawerLayout.closeDrawers();

        return true;
    }

    public View.OnClickListener onRoomListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RoomListFragment roomListFragment = new RoomListFragment();
            //roomListFragment.setArguments();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame_main, roomListFragment)
                    .addToBackStack(null)
                    .commit();

            //item.setChecked(true);
            drawerLayout.closeDrawers();
        }
    };
}
