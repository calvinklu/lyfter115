package lifter.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    Dialog myDialog;

    public MyScheduleList(Activity context, List<Schedule> myScheduleList){
        super(context, R.layout.activity_schedule_list, myScheduleList);
        this.context = context;
        this.myScheduleList = myScheduleList;
        myDialog = new Dialog(context);
    }

    public void showPopup(View v, Schedule s){
        final Schedule schedule = s;
        Button yes_btn;
        Button no_btn;
        myDialog.setContentView(R.layout.delete_popup);
        yes_btn =  myDialog.findViewById(R.id.yes_btn);
        no_btn = myDialog.findViewById(R.id.no_btn);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule(schedule);
                myDialog.dismiss();

            }
        });

        no_btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        }));
        myDialog.show();
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
        final TextView muscle = myListViewItem.findViewById(R.id.muscle);

        final Button delete_btn = myListViewItem.findViewById(R.id.delete_btn);
        final Button edit_btn = myListViewItem.findViewById(R.id.edit_btn);

        final Schedule mySchedule = myScheduleList.get(position);

        day.setText(mySchedule.getDay());
        from.setText(mySchedule.getFrom());
        to.setText(mySchedule.getTo());
        muscle.setText(mySchedule.getMuscle());


        edit_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddTimeActivity.class);
                //if you want to send data to called activity uncomment next line
                // intent.putExtra("extra", "value");

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


        delete_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v,mySchedule);
                    /*Schedule s = new Schedule(mySchedule.getId(),
                            mySchedule.getEmail(),
                            day.getText().toString(),
                            from.getText().toString(),
                            to.getText().toString(),
                            muscle.getText().toString());

                    /*schedule = s;
                    remove(mySchedule);
                    notifyDataSetChanged();
                    ref.child(schedule.getId()).removeValue();*/
                //deleteSchedule(mySchedule);
            }
        });
        return myListViewItem;

    }

    private void deleteSchedule(Schedule schedule) {
        ref.child(schedule.getId()).removeValue();
        myScheduleList.remove(schedule);
        notifyDataSetChanged();
    }
}