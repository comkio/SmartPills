package upf.edu.smartpills;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=4000;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logo Image
        logo = findViewById(R.id.logo);
        //Logo animation
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        logo.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //If !(table empty)
                Intent homeIntent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(homeIntent);
                //Transition Animation
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //Different type of animations
                //overridePendingTransition(R.anim.left_in, R.anim.left_out);
                //overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                //overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                finish();
                //else
                //Intent homeIntent = new Intent(FirstScreenLogo.this, Welcome.class);
                //startActivity(homeIntent);
                //overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                //finish();
            }
        },SPLASH_TIME_OUT);

        MyAppDatabase db = Room.databaseBuilder(getApplicationContext(),
                MyAppDatabase.class, "SPDatabase").allowMainThreadQueries().build();
    }

}
