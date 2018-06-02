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



public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Inici variables per fer el gir.
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button_nom);
        final EditText edit =  (EditText) findViewById(R.id.editTextW);

        //Gestionar la introducció del nom


        //Quan es pitja el boto, que giri i canvii de activity
        button.setOnClickListener(new View.OnClickListener() {
            ImageView loading = (ImageView) findViewById(R.id.imageView);
            AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();
            String nom;
            boolean isRotated = true;
            public void onClick(View v) {
                nom = edit.getText().toString();
                animation.start();
                startActivity(new Intent(WelcomeActivity.this, CalendarActivity.class));

            }

        });

    }
}
