package tech.sqlabs.shadowrunhelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private GoogleSignInClient googleSignInClient;
    private EditText mNick;

    SharedPreferences sharedPreferences;
    RxSharedPreferences rxPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNick = findViewById(R.id.Nick);

        sharedPreferences = getDefaultSharedPreferences(this);
        rxPreferences = RxSharedPreferences.create(sharedPreferences);

        Button SignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Const.SERVER_OAUTH_ID)
                .requestProfile()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton.setOnClickListener(v -> finishAuthorization());
        sendAuthorizationRequest();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "onActivityResult " + requestCode + " " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
        else {
            Log.e(TAG, "Authorization failed"
            );
        }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {

        Preference<String> token = rxPreferences.getString("token");
        String oAuthToken = googleSignInAccount.getIdToken();
        Log.v(TAG, "onLoggedIn");
        if (oAuthToken == null) {
            Log.e(TAG, "Token is null");
            return;
        } else {
            token.set(oAuthToken);
        }

        String name = googleSignInAccount.getDisplayName();
        Log.i(TAG, googleSignInAccount.getDisplayName());

        mNick.setText(name);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendAuthorizationRequest() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    private void finishAuthorization() {
        String nickname;
        if (mNick.getText() == null || mNick.getText().toString().equals("")) {
            nickname = "Incognito";
        } else {
            nickname = mNick.getText().toString();
        }
        Preference<String> nick = rxPreferences.getString("nick");
        nick.set(nickname);

        startMainActivity();
    }
}
