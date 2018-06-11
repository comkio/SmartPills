package upf.edu.smartpills;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NotificationsActivity extends AppCompatActivity {


    Button button;
    EditText title,descp;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        title = findViewById(R.id.editText6);
        descp = findViewById(R.id.editText7);
        spinner = findViewById(R.id.spinner);

        button = findViewById(R.id.notifButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotificationChannel(title.getText().toString(),descp.getText().toString(),spinner.getSelectedItem().toString());
                myNotifications(title.getText().toString(),descp.getText().toString(),spinner.getSelectedItem().toString());

            }
        });

    }






    private void createNotificationChannel(String tittle, String desc, String myImportance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = tittle;
            String description = desc;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(myImportance), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void myNotifications(String title, String desc, String importance){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, importance).
                setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.ic_notifications).
                setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_notifications)).setContentTitle(title).
                setContentText(desc);
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
