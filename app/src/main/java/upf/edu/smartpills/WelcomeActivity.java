package upf.edu.smartpills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static upf.edu.smartpills.FirstActivity.db;


public class WelcomeActivity extends AppCompatActivity {
    //Activity that allows the user to input a User Name
    EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Initiate variable to create an animation
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button_nom);
        nombre = findViewById(R.id.editTextW);

        //On click start the animation and change activity
        button.setOnClickListener(new View.OnClickListener() {
            ViewGroup layout = (ViewGroup) button.getParent();
            ViewGroup lalala = (ViewGroup) nombre.getParent();
            String nom;

            public void onClick(View v) {
                //Error if the user name is empty
                if (TextUtils.isEmpty(nombre.getText())) {
                    nombre.setError("First name is required!");
                    Toast.makeText(getApplicationContext(), "User name is empty", Toast.LENGTH_SHORT).show();
                } else {
                    //Greet the user and add the user name to the DataBase
                    nom = nombre.getText().toString();
                    db.myDao().insertUsers(new User(nom));
                    layout.removeView(button);
                    lalala.removeView(nombre);

                    TextView myTextView = findViewById(R.id.textView4);
                    myTextView.setText("Hola, " + nom);

                    //Change to Calendar activity after 0.5 seconds
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(WelcomeActivity.this, CalendarActivity.class));
                        }
                    }, 500);
                }


            }

        });

    }
}
