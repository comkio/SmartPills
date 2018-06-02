package upf.edu.smartpills;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {


    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    private TextView nombre;
    FloatingActionButton button;
    TextView  day;
    Button btnCamera;
    ImageView imageCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                //Transition Animation
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                finish();
            }
        });

        //CURRENT DATE
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String[] mydat  = mydate.split(",");
        day = findViewById(R.id.textView2);
        day.append(mydat[0]);






        /*move to the fragmetn class
        /// //Camera Pic
        btnCamera = findViewById(R.id.btnCamera);
        imageCamera = findViewById(R.id.imageCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });*/



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
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
        super.onBackPressed();

    }
    //Picture Function
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageCamera.setImageBitmap(bitmap);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        TextView textView = (TextView) findViewById(R.id.textView);
                        switch (menuItem.getItemId()) {
                            case R.id.about:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                myDrawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.umaual:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                myDrawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.settings:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                myDrawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.restart:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                myDrawer.closeDrawer(GravityCompat.START);
                                return true;

                        }
                        return true;
                    }
                });
    }

}
