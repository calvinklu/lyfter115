package lifter.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    private Button register;
    private EditText etName, etEmail, etPassword;
    private TextView login, title;
    private ProgressBar progress;
    private static final String TAG = "register";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        login = (TextView) findViewById(R.id.login);
        title = (TextView) findViewById(R.id.title);

        register = (Button) findViewById(R.id.register);
        progress = (ProgressBar) findViewById(R.id.progress);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login:
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                break;

            case R.id.register:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    registerProcess(email, password);

                } else {

                    Snackbar.make(title, "Fields are empty!", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void registerProcess(String email, String password) {

        //AuthCredential credential = GoogleAuthProvider.getCredential(email, password);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();


                            Snackbar.make(title, "Login Successful", Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "Success!");

                            System.out.println(user);
                            System.out.println(user.getUid());
                            System.out.println(user.getDisplayName());

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                        } else {

                            Snackbar.make(title, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "fail!");
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
