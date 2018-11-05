package lifter.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "login";
    private EditText etEmail, etPassword;
    private Button login;
    private TextView register, title;
    private FirebaseAuth mAuth;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        register = (TextView) findViewById(R.id.register);
        title = (TextView) findViewById(R.id.title);

        login = (Button) findViewById(R.id.login);
        progress = (ProgressBar) findViewById(R.id.progress);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.register:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;

            case R.id.login:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);

                    loginProcess(email, password);

                } else {

                    Snackbar.make(title, "Fields are empty!", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void loginProcess(String email, String password) {

        //AuthCredential credential = GoogleAuthProvider.getCredential(email, password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();

                            Snackbar.make(title, "Login Successful", Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "Success!");

                            startActivity(new Intent(MainActivity.this, AddTimeActivity.class));

                        } else {

                            Snackbar.make(title, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "fail!");
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
