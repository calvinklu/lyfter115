package lifter.app;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class BackgroundService extends Service{
    private DatabaseReference ref;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Alarm service started...", Toast.LENGTH_LONG).show();
        workoutTimeCheck();
        return START_STICKY;
    }
    @Override
    public void onDestroy(){
        Toast.makeText(this, "Alarm service stopped...", Toast.LENGTH_LONG).show();
    }
    @Override
    public IBinder onBind(Intent intent){ return null; }
    //----------------------------------------
    public void workoutTimeCheck(){
        user = auth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        final List<String> dayTimes = new ArrayList<String>();
        final List<String> fromTimes = new ArrayList<String>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot daySnap = dataSnapshot.child("day");
                Iterable<DataSnapshot> dayChildren = daySnap.getChildren();
                DataSnapshot fromSnap = dataSnapshot.child("from");
                Iterable<DataSnapshot> fromChildren = fromSnap.getChildren();
                for(DataSnapshot day : dayChildren){
                    String workoutDay = dataSnapshot.child("day").getValue().toString();
                    dayTimes.add(workoutDay);
                }
                for(DataSnapshot from : fromChildren){
                    String workoutFrom = dataSnapshot.child("from").getValue().toString();
                    dayTimes.add(workoutFrom);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        String[] dayArray = dayTimes.toArray(new String[dayTimes.size()]);
        String[] fromArray = fromTimes.toArray(new String[fromTimes.size()]);
        String[] workoutDates = new String[dayTimes.size()];
        for(int i = 0; i < dayArray.length; i++){
            workoutDates[i] = dayArray[i] + "-" + fromArray[i];
        }
        SimpleDateFormat sdf =  new SimpleDateFormat("EEEE-HH:mm");
        String currentDate = sdf.format(new Date());
        //create workoutDates
        //String[] workoutDates = {"Monday-13:00", "Wednesday-14:30", "Friday-13:45"};
        //Convert workoutDates into alarmDates
        String[] alarmDates = new String[workoutDates.length];
        timeConv(workoutDates, alarmDates);
        for(int i = 0; i < alarmDates.length; i++){
            if(currentDate.equals(alarmDates[i])){
                String[] dayTime = new String[1];
                dayTime = alarmDates[i].split("-");
                sendNotification(dayTime[1]);
            }
        }
        System.out.println("It's not time for your workout yet");
    }
    public static void timeConv(String[] workoutDates, String[] alarmDates){
        for(int i = 0; i < workoutDates.length; i++){
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf =  new SimpleDateFormat("EEEE-HH:mm");
            try{
                Date date = sdf.parse(workoutDates[i]);
                cal.setTime(date);
            }
            catch(Exception e){
                System.out.println("Workout date not accepted.");
                System.exit(0);
            }
            cal.add(Calendar.MINUTE, -30);
            Date halfHourBack = cal.getTime();
            String alarmDate = sdf.format(halfHourBack);
            alarmDates[i] = alarmDate;
        }
    }
    //----------------------------------------
    private void sendNotification(String message) {
        Intent notifDest = new Intent(this, MySchedule.class);
        PendingIntent pendingNotifDest = PendingIntent.getActivity
                (this, 1, notifDest, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.flex_logo)
                .setContentTitle("Time for your workout!")
                .setContentText("Workout today in 30 minutes at " + message)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingNotifDest)
                .setAutoCancel(true);
        NotificationManager notifMan = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel Swole",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notifMan.createNotificationChannel(channel);
            notif.setChannelId(channelId);
        }
        notifMan.notify(0, notif.build());
    }
}
