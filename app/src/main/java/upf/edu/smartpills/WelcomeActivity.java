package upf.edu.smartpills;

import android.content.Intent;


import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Inici variables per fer el gir.
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button_nom);
        final EditText edit =  (EditText) findViewById(R.id.editTextW);

        //Quan es pitja el boto, que giri i canvii de activity
        button.setOnClickListener(new View.OnClickListener() {
            ImageView loading = (ImageView) findViewById(R.id.imageView);
            AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();
            ViewGroup layout = (ViewGroup) button.getParent();
            ViewGroup lalala = (ViewGroup) edit.getParent();
            String nom;

            public void onClick(View v) {
                nom = edit.getText().toString();
                animation.start();
                layout.removeView(button);
                lalala.removeView(edit);

                TextView myTextView = findViewById(R.id.textView4);
                myTextView.setText("Hola, " + nom);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() { startActivity(new Intent(WelcomeActivity.this, CalendarActivity.class));
                    animation.stop();
                    }
                }, 1300);


            }

        });

    }
}
