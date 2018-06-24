package upf.edu.smartpills;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static upf.edu.smartpills.FirstActivity.db;

//Implement NavigationView for our menu
public class CalendarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Declare interactable items
    FloatingActionButton button;
    TextView day, username;
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

        //Create drawer for the menu and set listener for the menu options
        DrawerLayout myDrawer = findViewById(R.id.myDrawer);
        ActionBarDrawerToggle myToggle = new ActionBarDrawerToggle(
                this, myDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        NavigationView nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(this);

        //Adapt the UserName that appears in the menu to the one entered in the DataBase
        username = nv.getHeaderView(0).findViewById(R.id.username);

        List<User> userList = db.myDao().getAllUsers();
        if (!userList.isEmpty()) {
            username.setText(userList.get(0).getName());
        }

        //If the plus button is clicked go to Treatments Activity to add treatments
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

        //Obtain current day and display it below the calendar
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String[] mydat = mydate.split(",");
        day = findViewById(R.id.textView2);
        day.append(mydat[0]);

        //Obtain pills and fromHour and display them below the calendar
        List<Pill> allpills = db.myDao().getAllPills();
        List<TreatmentPill> alltreatmentpills = db.myDao().getAllTreatmentPills();
        List<String> todaypills = new ArrayList<>();
        for (Pill pill : allpills) {
            todaypills.add(pill.getName() + "--" + db.myDao().getTreatmentPillByPillId(pill.getId()).getFromHour());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todaypills);
        listCalendar.setAdapter(adapter);


    }

    //Act depending on the item selected in the menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        //For each item selected in the menu go to its respective activity
        int id = menuItem.getItemId();

        if (id == R.id.about) {

            Intent profile = new Intent(CalendarActivity.this, AboutActivity.class);
            startActivity(profile);
            finish();
        } else if (id == R.id.umanual) {

            Intent umanual = new Intent(CalendarActivity.this, UserManualActivity.class);
            startActivity(umanual);
            finish();

        } else if (id == R.id.settings) {

            Intent profile = new Intent(CalendarActivity.this, SettingsActivity.class);
            startActivity(profile);
            finish();
        } else if (id == R.id.restart) {
            //In case of restart display Alert and delete all elements in the DataBase before
            //exiting to the WelcomeActivity
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

        } else if (id == R.id.notifications) {
            Intent profile = new Intent(CalendarActivity.this, NotificationsActivity.class);
            startActivity(profile);
            finish();
        } else if (id == R.id.buy) {
            //In case of refill show an alert and simply display a Toast
            final AlertDialog.Builder mbuilder = new AlertDialog.Builder(CalendarActivity.this);
            mbuilder.setTitle("Pharmacy");
            mbuilder.setMessage("Do you want to refill all your pills?");
            mbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(CalendarActivity.this, "Your pills will arrive soon", Toast.LENGTH_SHORT).show();
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

    //On back pressed close the menu if it's open or exit the app in the other case
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.myDrawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
