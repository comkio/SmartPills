package upf.edu.smartpills;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationsActivity extends AppCompatActivity {


    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        button = findViewById(R.id.notifButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNotifications();
            }
        });

    }

    public void myNotifications(){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.ic_notifications).
                setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_notifications)).setContentTitle("Pill Time!").
                setContentText("Its time for the blue pill;)");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notificationBuilder.build());
    }

    @Override
    public void onBackPressed() {
        Intent profile = new Intent(NotificationsActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
}
