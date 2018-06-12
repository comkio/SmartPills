package upf.edu.smartpills;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static upf.edu.smartpills.FirstActivity.db;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton button;
    TextView day;
    ImageView imageCamera;
    ListView listCalendar;
    CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listCalendar = findViewById(R.id.listCalendar);
        calendarView = findViewById(R.id.calendarView);
        //Menu Icon
        DrawerLayout myDrawer = findViewById(R.id.myDrawer);
        ActionBarDrawerToggle myToggle = new ActionBarDrawerToggle(
                this,myDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        NavigationView nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(this);


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
        String[] mydat = mydate.split(",");
        day = findViewById(R.id.textView2);
        day.append(mydat[0]);

        final ArrayList<String> pillNames = new ArrayList<String>();

        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

                int yearPill;
                int monthPill;
                int dayPill;
                String date;
                Log.d("date","hola");

                int tamany = FirstActivity.db.myDao().countTreatmentPills();


                Log.d("date","tamany: " + tamany);
                for (int i = 0; i < tamany; i++) {
                    date = FirstActivity.db.myDao().getAllTreatmentPills().get(i).getFrom();
                    Log.d("date", "date: " + date);
                    String[] parts = date.split("/");
                    dayPill = Integer.parseInt(parts[0]);
                    Log.d("date","dia: " + dayPill);
                    monthPill = Integer.parseInt(parts[1]);
                    Log.d("date","month: " + monthPill);
                    yearPill = Integer.parseInt(parts[2]);
                    Log.d("date","year: " + yearPill);

                    if((dayPill == dayOfMonth) && (monthPill == month) && (yearPill == year)){

                        int pillId = FirstActivity.db.myDao().getAllTreatmentPills().get(i).getPillId();
                        String pillName = FirstActivity.db.myDao().getPillbyId(pillId).name;

                        pillNames.add(pillName);

                    }
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pillNames);
        listCalendar.setAdapter(adapter);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.about) {

            Intent profile = new Intent(CalendarActivity.this, AboutActivity.class);
            startActivity(profile);
            finish();
        } else if (id == R.id.umanual) {

            Intent umanual = new Intent(CalendarActivity.this, UserManualActivity.class);
            startActivity(umanual);
            finish();
            /*CharSequence text = "Works!";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
        } else if (id == R.id.settings) {

            Intent profile = new Intent(CalendarActivity.this, SettingsActivity.class);
            startActivity(profile);
            finish();
        } else if (id == R.id.restart) {
            android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(
                    CalendarActivity.this);
            alert.setTitle("Alert!!");
            alert.setMessage("Are you sure you want to reset all your data?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do your work here
                    db.myDao().resetTreatmentPill();
                    db.myDao().resetTreatment();
                    db.myDao().resetUser();
                    db.myDao().resetPill();
                    dialog.dismiss();
                    Intent login = new Intent(CalendarActivity.this, WelcomeActivity.class);
                    startActivity(login);
                    finish();
                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();

        }else if (id == R.id.notifications) {
            Intent profile = new Intent(CalendarActivity.this, NotificationsActivity.class);
            startActivity(profile);
            finish();
        } else if (id == R.id.buy) {
            final AlertDialog.Builder mbuilder = new AlertDialog.Builder(CalendarActivity.this);
            mbuilder.setTitle("Pharmacy");
            mbuilder.setMessage("Do you want to refill all your pills?");
            mbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(CalendarActivity.this,"Your pills will arrive soon", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        DrawerLayout myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.myDrawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Picture Function
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageCamera.setImageBitmap(bitmap);
    }


}
