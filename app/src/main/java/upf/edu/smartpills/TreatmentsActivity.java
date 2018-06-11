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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
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
        treatmentList = findViewById(R.id.treatmentsList);

        //Button Action to another activity
        button = findViewById(R.id.floatingActionButton);

        List<String> values = new LinkedList<>();

        final int treatmentSize = db.myDao().countTreatments();
        for (int i = 0; i < treatmentSize; ++i) {
            values.add(db.myDao().getAllTreatments().get(i).getTreatmentName());

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        treatmentList.setAdapter(adapter);


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


        treatmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        TreatmentsActivity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                final int positionToRemove = position;
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        //db.myDao().deleteTreatment();
                        //treatmentList.removeViewAt(positionToRemove);
                        //adapter.notifyDataSetChanged();
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
