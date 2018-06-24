package upf.edu.smartpills;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import static upf.edu.smartpills.FirstActivity.db;

public class TreatmentsActivity extends AppCompatActivity {
//Activity showing the treatments allowing us to delete them or go to a new activity to add them

    ListView treatmentList;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);

        treatmentList = findViewById(R.id.treatmentsList);
        button = findViewById(R.id.floatingActionButton);

        //Add all the treatments from the database to a List
        List<String> values = new LinkedList<>();
        final int treatmentSize = db.myDao().countTreatments();
        for (int i = 0; i < treatmentSize; ++i) {
            values.add(db.myDao().getAllTreatments().get(i).getTreatmentName());

        }
        //Add the treatments to the listView showing them
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        treatmentList.setAdapter(adapter);

        //Clicking the plus button moves us to another activity to add new treatments
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


        //On click on one treatment on the list show an alert to delete said treatment
        treatmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        TreatmentsActivity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure you want to delete the treatment?");
                final int positionToRemove = position;
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //If the deletion is accepted remove the treatment from its table
                        //and delete the corresponding pills for said treatment
                        String removeTreatmentname = adapter.getItem(positionToRemove);
                        Treatment removeTreatment;
                        adapter.remove(removeTreatmentname);
                        adapter.notifyDataSetChanged();
                        List<Treatment> alltreatments = db.myDao().getAllTreatments();
                        for (int i = 0; i < treatmentSize; ++i) {
                            removeTreatment = alltreatments.get(i);
                            if (removeTreatment.getTreatmentName().equals(removeTreatmentname)) {
                                List<TreatmentPill> pillsInTreatment = db.myDao().getTreatmentPillByTreatmentId(removeTreatment.getId());
                                for (TreatmentPill tp : pillsInTreatment) {
                                    db.myDao().deletePill(db.myDao().getPillbyId(tp.getPillId()));
                                }
                                db.myDao().deleteTreatment(removeTreatment);
                            }
                        }
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

    //On back pressed go to the Calendar activity
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(TreatmentsActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }

}
