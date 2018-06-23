package upf.edu.smartpills;

import android.content.Intent;


import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static upf.edu.smartpills.FirstActivity.db;


public class WelcomeActivity extends AppCompatActivity {


    EditText nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Inici variables per fer el gir.
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button_nom);
        nombre =  findViewById(R.id.editTextW);

        //Quan es pitja el boto, que giri i canvii de activity
        button.setOnClickListener(new View.OnClickListener() {
            ViewGroup layout = (ViewGroup) button.getParent();
            ViewGroup lalala = (ViewGroup) nombre.getParent();
            String nom;

            public void onClick(View v) {

                if( TextUtils.isEmpty(nombre.getText())) {
                    nombre.setError("First name is required!");
                    Toast.makeText(getApplicationContext(), "User name is empty", Toast.LENGTH_SHORT).show();
                }else{
                    nom = nombre.getText().toString();
                    db.myDao().insertUsers(new User(nom));
                    layout.removeView(button);
                    lalala.removeView(nombre);

                    TextView myTextView = findViewById(R.id.textView4);
                    myTextView.setText("Hola, " + nom);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() { startActivity(new Intent(WelcomeActivity.this, CalendarActivity.class));
                        }
                    }, 500);
                }




            }

        });

    }
}
