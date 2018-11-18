package lifter.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import java.util.List;


public class MyWorkout extends AppCompatActivity {

    private Workout workout;

    FirebaseAuth auth;
    FirebaseUser u;
    DatabaseReference ref;

    ListView ListViewWorkout;
    List<Workout> myWorkoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);


        ref = FirebaseDatabase.getInstance().getReference("workout");

        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();

        Query my_query = FirebaseDatabase.getInstance().getReference("workout")
                .orderByChild("email")
                .equalTo(u.getEmail());

        my_query.addListenerForSingleValueEvent(my_listener);
        ListViewWorkout = (ListView) findViewById(R.id.ListViewWorkout);
        myWorkoutList = new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddWorkoutActivity.class);
                startActivity(i);
            }
        });
    }

    protected void onResume() {
        super.onResume();

    }
    //
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
            myWorkoutList.clear();
            for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                // do something with the schedules
                Workout workout = workoutSnapshot.getValue(Workout.class);
                myWorkoutList.add(workout);
            }
            MyWorkoutList myAdapter = new MyWorkoutList(MyWorkout.this, myWorkoutList);
            ListViewWorkout.setAdapter(myAdapter);

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
