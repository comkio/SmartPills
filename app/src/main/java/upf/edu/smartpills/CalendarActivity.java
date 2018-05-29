package upf.edu.smartpills;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {


    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    private TextView nombre;
    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //Menu Icon
        myDrawer = findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this,myDrawer,R.string.open,R.string.close);

        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Getting the name
        //nombre = findViewById(R.id.textView);
        //nombre.append(" "+USER.GETNAME);

        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(CalendarActivity.this, TreatmentsActivity.class);
                startActivity(profile);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(myToggle.onOptionsItemSelected(item))
           return true;

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //NavUtils.navigateUpFromSameTask(this);
        //finish();
    }
}
