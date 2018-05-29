package upf.edu.smartpills;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TreatmentsActivity extends AppCompatActivity {


    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);



        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(TreatmentsActivity.this, NewTreatmentActivity.class);
                startActivity(profile);
                finish();
            }
        });

    }
}
