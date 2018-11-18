package lifter.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class WorkoutList extends ArrayAdapter<Workout> {
    private Activity context;
    private List<Workout> workoutList;

    public WorkoutList(Activity context, List<Workout> workoutList){
        super(context, R.layout.list_layout, workoutList);
        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView muscle = listViewItem.findViewById(R.id.muscle);

        Workout workout = workoutList.get(position);
        muscle.setText(workout.getMuscle());

        return listViewItem;
    }

}