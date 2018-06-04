package upf.edu.smartpills;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FirstActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=4000;
    private ImageView logo;

    MyAppDatabase db = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class, "SmartPills").allowMainThreadQueries().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);




        //Logo Image
        logo = findViewById(R.id.logo);
        //Logo animation
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        logo.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(db.myDao().countUsers()>0) {
                    Intent homeIntent = new Intent(FirstActivity.this, WelcomeActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(homeIntent);

                    finish();
                }else{
                    Intent homeIntent = new Intent(FirstActivity.this, CalendarActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(homeIntent);

                    finish();
                }




                //Transition Animation

                //Different type of animations
                //overridePendingTransition(R.anim.left_in, R.anim.left_out);
                //overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                //overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);

                //else
                //
                //startActivity(homeIntent);
                //overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                //finish();

            }
        },SPLASH_TIME_OUT);


    }

}
