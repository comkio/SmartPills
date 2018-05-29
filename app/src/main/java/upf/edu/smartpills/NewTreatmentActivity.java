package upf.edu.smartpills;

import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NewTreatmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatment);

        //Testing Animation
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final TextView text = transitionsContainer.findViewById(R.id.text);
        final Button button = transitionsContainer.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            boolean visible;

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                visible = !visible;
                text.setVisibility(visible ? View.VISIBLE : View.GONE);
            }

        });
        //

    }
}
