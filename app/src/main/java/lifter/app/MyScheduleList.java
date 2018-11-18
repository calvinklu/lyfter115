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

public class MyScheduleList extends ArrayAdapter<Schedule> {

        private Activity context;
        private List<Schedule> myScheduleList;
        private Schedule schedule;
        private View myListViewItem;

        private DatabaseReference ref;


        public MyScheduleList(Activity context, List<Schedule> myScheduleList){
            super(context, R.layout.activity_schedule_list, myScheduleList);
            this.context = context;
            this.myScheduleList = myScheduleList;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            myListViewItem = inflater.inflate(R.layout.activity_schedule_list, null, true);

            ref = FirebaseDatabase.getInstance().getReference("schedule");

            final TextView day = myListViewItem.findViewById(R.id.day);
            final TextView from = myListViewItem.findViewById(R.id.from);
            final TextView to = myListViewItem.findViewById(R.id.to);

            final Button delete_btn = myListViewItem.findViewById(R.id.delete_btn);

            final Schedule mySchedule = myScheduleList.get(position);
            day.setText(mySchedule.getDay());
            from.setText(mySchedule.getFrom());
            to.setText(mySchedule.getTo());

            delete_btn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Schedule s = new Schedule(mySchedule.getId(),
                            mySchedule.getEmail(),
                            day.getText().toString(),
                            from.getText().toString(),
                            to.getText().toString());

                    schedule = s;
                    deleteSchedule(schedule);
                }
            });
            return myListViewItem;
        }

        private void deleteSchedule(Schedule schedule) {
            ref.child(schedule.getId()).removeValue();
            myScheduleList.remove(schedule);
        }
}

