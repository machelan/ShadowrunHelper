package tech.sqlabs.shadowrunhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button CreateButton = findViewById(R.id.CREATE_BUTTON);
        final Button LoadButton = findViewById(R.id.LOAD_BUTTON);
        CreateButton.setOnClickListener(v -> {
            Log.d(TAG, "Try to start activity");
            Intent i = new Intent(this, CreateCharacterActivity.class);
            startActivity(i);
        });
        LoadButton.setOnClickListener(v -> {
            Intent i = new Intent(this, LoadCharacterActivity.class);
            startActivity(i);
        });
    }
}
