package tech.sqlabs.shadowrunhelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import tech.sqlabs.shadowrunhelper.character.character;
import tech.sqlabs.shadowrunhelper.character.characterDBAction;

public class CreateCharacterActivity extends AppCompatActivity {
    private static final String TAG = "CreateCharacterActivity";
    private characterDBAction db;
    private int mAge;
    private String mName;
    private String mNationality;
    private int mGenderID;
    private String mMetatype;

    private void loadDefaultValues() {
        mAge = Integer.parseInt(getString(R.string.ageHint));
        mName = getString(R.string.nameHint);
        mNationality = getString(R.string.nationalityHint);
        mGenderID = 0;
        mMetatype = getString(R.string.metatypeHint);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Create character activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        db = new characterDBAction(getApplicationContext());

        ((Spinner)findViewById(R.id.gender)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGenderID = (int)id;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });



        final Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"Create button",Toast.LENGTH_SHORT).show();
            /*Log.d(TAG, "Try to start activity");
            Intent i = new Intent(this, CreateCharacterActivity.class);
            startActivity(i);*/

            loadDefaultValues();

            String ageText = String.valueOf(((EditText)findViewById(R.id.age)).getText());
            String nameText = String.valueOf(((EditText)findViewById(R.id.name)).getText());
            String nationalityText = String.valueOf(((EditText)findViewById(R.id.nationality)).getText());
            String metatypeText = String.valueOf(((EditText)findViewById(R.id.metatype)).getText());

            if (!ageText.isEmpty())
                mAge = Integer.parseInt(ageText);
            if (!nameText.isEmpty())
                mName = nameText;
            if (!nationalityText.isEmpty())
                mNationality = nationalityText;
            if (!metatypeText.isEmpty())
                mMetatype = metatypeText;
            db.addRec(new character(mAge, mName, mNationality, (mGenderID == 0 ? "male": "female"), mMetatype));
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

    public void hideKeyboard(View view1) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
