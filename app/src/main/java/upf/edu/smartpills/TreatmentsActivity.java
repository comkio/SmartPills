package upf.edu.smartpills;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static upf.edu.smartpills.FirstActivity.db;

public class TreatmentsActivity extends AppCompatActivity {



    ListView treatmentList;
    FloatingActionButton button;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        treatmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        TreatmentsActivity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();


            }
        });
        //Putting the treatments name into the listview***
        //String[] tratamientos = new String[];
        //List<Treatment> t = db.myDao().getAllTreatments();
        //for (int i = 0; i < t.size(); ++i) {
         //   tratamientos[i] = t.get(i).getUserName();

        //}
        //adapter = new ListAdapter(this, android.R.layout.simple_list_item_1, tratamientos);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent profile = new Intent(TreatmentsActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
    @Override
    public Intent getSupportParentActivityIntent(){
        Intent profile = new Intent(TreatmentsActivity.this, CalendarActivity.class);
        return profile;
    }
    @Override
    public Intent getParentActivityIntent(){
        Intent profile = new Intent(TreatmentsActivity.this, CalendarActivity.class);
        return profile;
    }
}
