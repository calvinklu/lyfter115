package lifter.app;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class AddTimeActivity extends AppCompatActivity {
    Button fromBtn, toBtn, add,  exit;
    Spinner day;
    ProgressBar progress;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser u = auth.getCurrentUser();
    int fromHours, fromMinute, toHour, toMinute;
    String user_email = u.getEmail();

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
//        auth = FirebaseAuth.getInstance();
//        u = auth.getCurrentUser();



        exit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AddTimeActivity.this, Sidebar.class);
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


    public interface MyCallback {
        void onCallback(AtomicBoolean value);
    }


    private void addSchedule(DatabaseReference ref){

        final Bundle extras = new Bundle();

        final String fromTime = fromBtn.getText().toString();
        final String toTime = toBtn.getText().toString();
        final String etDay = day.getSelectedItem().toString();
        final String email_content = u.getEmail();
        FirebaseDatabase databaseSchedule = FirebaseDatabase.getInstance();
        final DatabaseReference reference = databaseSchedule.getReference("schedule");

        if(!TextUtils.isEmpty(fromTime)
                && !TextUtils.isEmpty(toTime)
                && !TextUtils.isEmpty(etDay)) {

                // toHour must be greater than fromHour
                // reserved range of time must be at least an hour
                if (fromHours < toHour && (toHour - fromHours >= 1)) {

             //       final AtomicBoolean conflicted = new AtomicBoolean(false);
                    ref.orderByChild("email").equalTo(user_email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String workout_day = "";
                            int new_ftime = Integer.valueOf(fromTime.replaceAll("[^\\d.]", ""));
                            int new_ttime = Integer.valueOf(toTime.replaceAll("[^\\d.]", ""));
                            boolean before_ok = false;
                            boolean after_ok = false;
                            boolean conflict = false;

                            for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                workout_day = datas.child("day").getValue().toString();
                                if (workout_day.equals(etDay)) {
                                    String fr_time = datas.child("from").getValue().toString();
                                    String to_time = datas.child("to").getValue().toString();
                                    String from = datas.child("from").getValue().toString();
                                    String to = datas.child("to").getValue().toString();
                                    fr_time = fr_time.replaceAll("[^\\d.]", "");
                                    to_time = to_time.replaceAll("[^\\d.]", "");
                                    int old_ftime = Integer.valueOf(fr_time);
                                    int old_ttime = Integer.valueOf(to_time);
                                    //check to see if the new workout can be before or after the iterated workout
                                    if ((new_ftime <= old_ftime && new_ttime <= old_ftime)) {
                                        before_ok = true;
                                    } else {
                                        before_ok = false;
                                    }
                                    //if the new workout's start time is before the old workouts end time
                                    if (new_ftime >= old_ttime) { //checks to see if can start a workout after another workout
                                        after_ok = true;
                                    } else {
                                        after_ok = false;
                                    }
                                    //if you cannot put in before or after
                                    if (!(before_ok || after_ok)) {
                                        message("Your new workout overlaps with one of the current workouts " + from + " to " + to);
                                        conflict = true;
                                        break;
                                    }
                                }
                            }
                            if (conflict == true) {
                                        message("Please choose another schedule");
                            }
                            else{
                                        String id = reference.push().getKey();

                                        Intent i = new Intent(AddTimeActivity.this, AddWorkoutActivity.class);

                                        extras.putString("id", id);
                                        extras.putString("email_content", email_content);
                                        extras.putString("day", etDay);
                                        extras.putString("fromTime", fromTime);
                                        extras.putString("toTime", toTime);

                                        i.putExtras(extras);
                                        startActivity(i);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }
                else
                    Toast.makeText(this, "You have not filled a correct time slot", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "You have not filled out a required field", Toast.LENGTH_LONG).show();


    }



    public void message(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }





    //clock for "From" option
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

    //clock for "To" option
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