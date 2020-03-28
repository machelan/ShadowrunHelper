package tech.sqlabs.shadowrunhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button CreateButton = findViewById(R.id.CREATE_BUTTON);
        final Button LoadButton = findViewById(R.id.LOAD_BUTTON);

        SharedPreferences sharedPreferences = getDefaultSharedPreferences(this);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(sharedPreferences);
        Preference<String> token = rxPreferences.getString("token");
        Preference<String> nick = rxPreferences.getString("nick");
        String oAuthToken = token.get();
        String nickname = nick.get();
        if (oAuthToken.equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.i(TAG, "Auth token:");
            Log.i(TAG, oAuthToken);
            Log.i(TAG, "Nick:");
            Log.i(TAG, nickname);
        }

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
