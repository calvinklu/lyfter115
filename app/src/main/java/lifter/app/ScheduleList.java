package lifter.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class ScheduleList extends ArrayAdapter<Schedule> {
    private Activity context;
    private List<Schedule> scheduleList;

    public ScheduleList(Activity context, List<Schedule> scheduleList){
        super(context, R.layout.list_layout, scheduleList);
        Collections.reverse(scheduleList);
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView day = listViewItem.findViewById(R.id.day);
        TextView from = listViewItem.findViewById(R.id.from);
        TextView to = listViewItem.findViewById(R.id.to);
        TextView muscle = listViewItem.findViewById(R.id.muscle);

        Schedule schedule = scheduleList.get(position);
        day.setText(schedule.getDay());
        from.setText(schedule.getFrom());
        to.setText(schedule.getTo());
        muscle.setText(schedule.getMuscle());

        return listViewItem;
    }

}
