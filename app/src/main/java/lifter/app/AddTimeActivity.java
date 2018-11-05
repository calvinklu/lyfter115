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

import java.util.Calendar;


public class AddTimeActivity extends AppCompatActivity {
    Button fromBtn, toBtn, add,  exit;
    Spinner day;
    ProgressBar progress;

    FirebaseAuth auth;
    FirebaseUser u;

    int fromHours, fromMinute, toHour, toMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_time);

        fromBtn = (Button) findViewById(R.id.from);
        toBtn = (Button) findViewById(R.id.to);
        day = (Spinner) findViewById(R.id.day);
        add = (Button) findViewById(R.id.add);
        exit = (Button) findViewById(R.id.exit);
        progress = (ProgressBar) findViewById(R.id.progress);


        FirebaseDatabase databaseSchedule = FirebaseDatabase.getInstance();

        final DatabaseReference ref = databaseSchedule.getReference("schedule");

        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();



        exit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AddTimeActivity.this, MySchedule.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addSchedule(ref);
            }
        });
    }


    private void addSchedule(DatabaseReference ref){

        String fromTime = fromBtn.getText().toString();
        String toTime = toBtn.getText().toString();
        String etDay = day.getSelectedItem().toString();
        String email_content = u.getEmail();

        if(!TextUtils.isEmpty(fromTime)
                && !TextUtils.isEmpty(toTime)
                && !TextUtils.isEmpty(etDay)) {

                // toHour must be greater than fromHour
                // reserved range of time must be at least an hour
                if (fromHours < toHour && (toHour - fromHours >= 1)) {

                    String id = ref.push().getKey();

                    // Reflects in Schedule java file (the scheduling object)
                    Schedule schedule = new Schedule(id, email_content, etDay, fromTime, toTime);
                    ref.child(id).setValue(schedule);

                    Intent i = new Intent(AddTimeActivity.this, MySchedule.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(this, "You have not filled a correct time slot", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "You have not filled out a required field", Toast.LENGTH_LONG).show();
    }


    public void setFromBtn(View v) {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog from;
        from = new TimePickerDialog(AddTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = "";
                Calendar datetime = Calendar.getInstance();
                fromHours = hourOfDay;
                fromMinute = minute;

                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR) + "";
                String strMinToShow = String.valueOf(datetime.get(Calendar.MINUTE));

                if (strMinToShow.length() == 1)
                    strMinToShow = "0" + strMinToShow;

                fromBtn.setText(strHrsToShow + ":" + strMinToShow + " " + am_pm);
            }
        },hour, minute,false);
        from.show();
    }


    public void setToBtn(View v) {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog to;
        to = new TimePickerDialog(AddTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = "";
                Calendar datetime = Calendar.getInstance();
                toHour = hourOfDay;
                toMinute = minute;

                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR) + "";
                String strMinToShow = String.valueOf(datetime.get(Calendar.MINUTE));

                if (strMinToShow.length() == 1)
                    strMinToShow = "0" + strMinToShow;

                toBtn.setText(strHrsToShow + ":" + strMinToShow + " " + am_pm);
            }
        },hour, minute,false);
        to.show();
    }
}