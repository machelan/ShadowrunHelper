package tech.sqlabs.shadowrunhelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import tech.sqlabs.shadowrunhelper.character.character;
import tech.sqlabs.shadowrunhelper.character.characterDBAction;

public class LoadCharacterActivity extends AppCompatActivity {
    private static final String TAG = "LoadCharacterActivity";
    private characterDBAction db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_character);
        LinearLayout layout = findViewById(R.id.loadLayout);
        db = new characterDBAction(getApplicationContext());
        List<character> characterList = db.getRecAll();
        for (character c : characterList) {
            Log.d(TAG, c.toString());
            TextView dynamicTextView = new TextView(this);
            dynamicTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dynamicTextView.setText(c.toString());
            layout.addView(dynamicTextView);
        }
    }
}
