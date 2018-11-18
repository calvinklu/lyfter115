package lifter.app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyWorkoutList extends ArrayAdapter<Workout> {

    private Activity context;
    private List<Workout> myWorkoutList;
    private Workout workout;
    private View myListViewItem;

    private DatabaseReference ref;


    public MyWorkoutList(Activity context, List<Workout> myWorkoutList){
        super(context, R.layout.activity_workout_list, myWorkoutList);
        this.context = context;
        this.myWorkoutList = myWorkoutList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        myListViewItem = inflater.inflate(R.layout.activity_workout_list, null, true);

        ref = FirebaseDatabase.getInstance().getReference("workout");

        final TextView muscle = myListViewItem.findViewById(R.id.muscle);

        final Button delete_btn = myListViewItem.findViewById(R.id.delete_btn);

        final Workout myWorkout = myWorkoutList.get(position);
        muscle.setText(myWorkout.getMuscle());

        delete_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workout w = new Workout(myWorkout.getId(),
                        myWorkout.getEmail(),
                        muscle.getText().toString());

                workout = w;
                deleteWorkout(workout);
            }
        });
        return myListViewItem;
    }

    private void deleteWorkout(Workout workout) {
        ref.child(workout.getId()).removeValue();
        myWorkoutList.remove(workout);
    }
}

