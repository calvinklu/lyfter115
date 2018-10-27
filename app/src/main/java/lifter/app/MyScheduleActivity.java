package lifter.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MyScheduleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        setTitle("My Schedule");

//        ImageView CouchPotato = (ImageView) findViewById(R.id.CouchPotato);
//        int imageResource = getResources().getIdentifier("@drawable/couchpotato", null, this.getPackageName());
//        CouchPotato.setImageResource(imageResource);

    }
}
