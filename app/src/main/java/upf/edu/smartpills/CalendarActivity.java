package upf.edu.smartpills;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {


    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    private TextView nombre;
    FloatingActionButton button;
    TextView  day;
    Button btnCamera;
    ImageView imageCamera;
    ListView listCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listCalendar = findViewById(R.id.listCalendar);
        //Menu Icon
        myDrawer = findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this,myDrawer,R.string.open,R.string.close);
        NavigationView nv = findViewById(R.id.nv);
        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.about:
                                fragmentManager.beginTransaction().replace(R.id.flcontent,new About()).commit();
                                //fragment = new About();
                                //fragmentTransaction = true;
                                break;
                            case R.id.umaual:
                                fragmentManager.beginTransaction().replace(R.id.flcontent,new UserManual()).commit();
                                //fragment = new UserManual();
                                //fragmentTransaction = true;
                                break;
                            case R.id.settings:
                                fragmentManager.beginTransaction().replace(R.id.flcontent,new Settings()).commit();
                                //fragment = new Settings();
                                //fragmentTransaction = true;
                                break;
                            case R.id.restart:
                                fragmentManager.beginTransaction().replace(R.id.myDrawer,new Restart()).commit();
                                //fragment = new Restart();
                                //fragmentTransaction = true;
                                break;
                            case R.id.btnCamera:
                                btnCamera = findViewById(R.id.btnCamera);
                                imageCamera = findViewById(R.id.imageCamera);
                                btnCamera.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent,0);
                                    }
                                });
                                break;


                        }
                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.flcontent, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        myDrawer.closeDrawers();

                        return true;
                    }
                });

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


        //List to be shown in the ListView
        //Need List of pill name form DB
        //String[] todayPills =null;
        //final ArrayList<String> list = new ArrayList<String>();
        //for (int i = 0; i < todayPills.length; ++i) {
//            list.add(todayPills[i]);
  //      }
    //    ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
      //  listCalendar.setAdapter(adapter);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my_menu, menu);
      //  MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.my_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myToggle.onOptionsItemSelected(item)) {
            return true;
        }else {

            switch(item.getItemId()) {
                case R.id.about:
                    Log.d("ActionItemClicked", "About clicked********");
                    return true;
                case R.id.umaual:
                    Log.d("ActionItemClicked", "User Manual clicked");
                    return true;
                case R.id.settings:
                    Log.d("ActionItemClicked", "Settings clicked");
                    return true;
                case R.id.restart:
                    Log.d("ActionItemClicked", "Restart clicked");
                    return true;
            }
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

    @Override
    public Intent getSupportParentActivityIntent(){
        return getSupportParentActivityIntent();
    }
    @Override
    public Intent getParentActivityIntent(){
        return getSupportParentActivityIntent();
    }

}
