package lifter.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class ScheduleList extends ArrayAdapter<Schedule>{
    private Activity context;
    private List<Schedule> scheduleList;

    public ScheduleList(Activity context, List<Schedule> scheduleList){
        super(context, R.layout.list_layout, scheduleList);
        //super(context, R.layout.activity_schedule_list, scheduleList);
//        Collections.reverse(scheduleList);
        Collections.sort(scheduleList, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule schedule, Schedule t1) {
                return schedule.getDay().compareTo(t1.getDay());
            }
        });
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        //View listViewItem = inflater.inflate(R.layout.activity_schedule_list, null, true);
        final TextView day = listViewItem.findViewById(R.id.day);
        final TextView from = listViewItem.findViewById(R.id.from);
        final TextView to = listViewItem.findViewById(R.id.to);
        final TextView muscle = listViewItem.findViewById(R.id.muscle);

        final Schedule schedule = scheduleList.get(position);
        day.setText(schedule.getDay());
        from.setText(schedule.getFrom());
        to.setText(schedule.getTo());
        muscle.setText(schedule.getMuscle());


        return listViewItem;
    }
}
