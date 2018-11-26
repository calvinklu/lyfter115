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

    Button add,  exit, back;
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
        //back = findViewById(R.id.back);
        final Bundle timeBundle = savedInstanceState;


        FirebaseDatabase databaseSchedule = FirebaseDatabase.getInstance();
        final DatabaseReference ref = databaseSchedule.getReference("schedule");

        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();


        exit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AddWorkoutActivity.this, Sidebar.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        /*back.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goBack(v,timeBundle);
            }
        });*/


        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addWorkout(ref);
            }
        });
    }

    public void goBack(View v, Bundle time){
        Intent i = new Intent(AddWorkoutActivity.this, AddTimeActivity.class);
        i.putExtras(time);
        startActivity(i);

    }
    private void addWorkout(DatabaseReference ref) {

        String etMuscle = muscle.getSelectedItem().toString();

        if (!TextUtils.isEmpty(etMuscle)) {

            Intent i = getIntent();
            Bundle extras = i.getExtras();

            String id = extras.getString("id");
            String email_content = extras.getString("email_content");
            String day = extras.getString("day");
            String fromTime = extras.getString("fromTime");
            String toTime = extras.getString("toTime");

            // Reflects in Schedule java file (the scheduling object)
            Schedule schedule = new Schedule(id, email_content, day, fromTime, toTime, etMuscle);
            ref.child(id).setValue(schedule);

            Intent j = new Intent(AddWorkoutActivity.this, Sidebar.class);
            startActivity(j);

        } else
            Toast.makeText(this, "You have not selected a muscle", Toast.LENGTH_LONG).show();
    }



}
