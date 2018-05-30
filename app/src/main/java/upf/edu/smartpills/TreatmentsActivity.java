package upf.edu.smartpills;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TreatmentsActivity extends AppCompatActivity {



    ListView treatmentList;
    ListView pillsList;
    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);


        //Button Action to another activity
        button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(TreatmentsActivity.this, NewTreatmentActivity.class);
                startActivity(profile);
                //Transition Animation
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        });

        treatmentList = findViewById(R.id.treatmentsList);
        pillsList = findViewById(R.id.pillsList);

        //Putting the treatments name into the listview***
        //ArrayList<String> treatments = new ArrayList<>();
        //get all treatment names of db
        //String[] treatmentsName = db.getTreatmentsName()?;
        //for (int i = 0; i < treamentsName.length; ++i) {
        //    treatments.add(values[i]);
        //}
        //ListAdapter adapter = new ListAdapter(this, android.R.layout.simple_list_item_1, treatments);
        //treatmentList.setAdapter(adapter);

        //Putting the pills name into the listview***
        //ArrayList<String> pills = new ArrayList<>();
        //get all pill names of db
        //String[] pillsName = db.getTreatmentsName()?;
        //for (int i = 0; i < pillsName.length; ++i) {
        //    pills.add(values[i]);
        //}
        //ListAdapter adapter = new ListAdapter(this, android.R.layout.simple_list_item_1, pills);
        //pillList.setAdapter(adapter);
    }
}
