package upf.edu.smartpills;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NotificationsActivity extends AppCompatActivity {
    //Notifications activity to show an example notification
    Button button;
    EditText title, descp;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Notifications");
        setContentView(R.layout.activity_notifications);


        title = findViewById(R.id.editText6);
        descp = findViewById(R.id.editText7);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.notifButton);

        //Create a notification with the input from the user and the parameters selected.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(title.getText()) && TextUtils.isEmpty(descp.getText())) {
                    title.setError("Title is required!");
                    descp.setError("Description is required!");
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    createNotificationChannel(title.getText().toString(), descp.getText().toString(), spinner.getSelectedItem().toString());
                    myNotifications(title.getText().toString(), descp.getText().toString(), spinner.getSelectedItem().toString());
                    Toast.makeText(NotificationsActivity.this, "Your notificatinos have been modified", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    //Method to create the notification, with description, tittle and importance
    private void createNotificationChannel(String tittle, String desc, String myImportance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = tittle;
            String description = desc;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(myImportance), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //Method to start the notification with icon and information set by the user
    public void myNotifications(String title, String desc, String importance) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, importance).
                setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.ic_notifications).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications)).setContentTitle(title).
                setContentText(desc);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    //Go back to the Calendar activity if back is pressed
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(NotificationsActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
}
