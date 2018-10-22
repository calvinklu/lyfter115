package lifter.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProfilePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Button AboutMeActivityBtn = (Button)findViewById(R.id.AboutMebutton);
        AboutMeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent1 = new Intent(getApplicationContext(), AboutMeActivity.class);
                startActivity(startIntent1); //allows to go to About Me page

            }
        });

        Button MyWorkoutsBtn = (Button)findViewById(R.id.MyWorkoutsbutton);
        MyWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent2 = new Intent(getApplicationContext(), MyWorkoutsActivity.class);
                startActivity(startIntent2); //allows to go to My Workouts page
            }
        });

        Button MyScheduleBtn = (Button)findViewById(R.id.MySchedulebutton);
        MyScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent3 = new Intent(getApplicationContext(), MyScheduleActivity.class);
                startActivity(startIntent3); //allows to go to My Schedule page
            }
        });


    }
}
