package lifter.app;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MySchedule extends AppCompatActivity{

    private Schedule schedule;

    Dialog myDialog;

    FirebaseAuth auth;
    FirebaseUser u;
    DatabaseReference ref;

    ListView ListViewSchedule;
    List<Schedule> myScheduleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        myDialog = new Dialog(this);

        ref = FirebaseDatabase.getInstance().getReference("schedule");

        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();

        Query my_query = FirebaseDatabase.getInstance().getReference("schedule")
                .orderByChild("email")
                .equalTo(u.getEmail());

        my_query.addListenerForSingleValueEvent(my_listener);
        ListViewSchedule = (ListView) findViewById(R.id.ListViewSchedule);
        myScheduleList = new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddTimeActivity.class);
                startActivity(i);
            }
        });
    }



    protected void onResume() {
        super.onResume();

    }


    // This method will just show the menu item (which is our button "ADD")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        // the menu being referenced here is the menu.xml from res/menu/menu.xml
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//
//
    ValueEventListener my_listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            myScheduleList.clear();
            for (DataSnapshot scheduleSnapshot : dataSnapshot.getChildren()) {
                // do something with the schedules
                Schedule schedule = scheduleSnapshot.getValue(Schedule.class);
                myScheduleList.add(schedule);
            }
            Collections.reverse(myScheduleList);
            MyScheduleList myAdapter = new MyScheduleList(MySchedule.this, myScheduleList);
            ListViewSchedule.setAdapter(myAdapter);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                /*the R.id.action_favorite is the ID of our button (defined in strings.xml).
                Change Activity here (if that's what you're intending to do, which is probably is).
                 */
                Intent i = new Intent(this, Sidebar.class);
                startActivity(i);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}
