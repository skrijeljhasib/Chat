package skrijeljhasib.chat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import skrijeljhasib.chat.R;

public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = getActivity().findViewById(R.id.main_layout);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public View.OnClickListener onRoomListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("show rooms");
            showRoomList();
        }
    };

    public void showRoomList() {
        RoomListFragment roomListFragment = new RoomListFragment();
        //roomListFragment.setArguments();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame_main, roomListFragment)
                .commit();

        //item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getItemId());

        RoomFragment roomFragment = new RoomFragment();
        //roomFragment.setArguments();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame_main, roomFragment)
                .commit();

        //item.setChecked(true);
        drawerLayout.closeDrawers();

        return true;
    }
}
