package lifter.app;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class AddWorkoutActivity extends AppCompatActivity {

    Button add,  exit;
    Spinner muscle;
    ProgressBar progress;

    FirebaseAuth auth;
    FirebaseUser u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        muscle = (Spinner) findViewById(R.id.muscle);
        add = (Button) findViewById(R.id.add);
        exit = (Button) findViewById(R.id.exit);
        progress = (ProgressBar) findViewById(R.id.progress);


        FirebaseDatabase databaseWorkout = FirebaseDatabase.getInstance();
        final DatabaseReference ref = databaseWorkout.getReference("workout");

        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();


        exit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AddWorkoutActivity.this, MyWorkout.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addWorkout(ref);
            }
        });
    }


    private void addWorkout(DatabaseReference ref) {

        String etMuscle = muscle.getSelectedItem().toString();
        String email_content = u.getEmail();

        if (!TextUtils.isEmpty(etMuscle)) {

            String id = ref.push().getKey();

            // Reflects in Schedule java file (the scheduling object)
            Workout workout = new Workout(id, email_content, etMuscle);
            ref.child(id).setValue(workout);

            Intent i = new Intent(AddWorkoutActivity.this, MyWorkout.class);
            startActivity(i);

        } else
            Toast.makeText(this, "You have not selected a muscle", Toast.LENGTH_LONG).show();
    }



}
