package skrijeljhasib.chat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import skrijeljhasib.chat.R;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void connect(View view) {
        Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
