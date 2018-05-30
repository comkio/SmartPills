package upf.edu.smartpills;

import android.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        //Menu Icon
        myDrawer = findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this,myDrawer,R.string.open,R.string.close);

        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();
        NavigationView nv = findViewById(R.id.nv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nv);

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
    //Picture Function
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageCamera.setImageBitmap(bitmap);
    }

    public void selectItemDrawer(MenuItem item){

        android.support.v4.app.Fragment myFragment = null;

        Class fragmentClass;
        switch(item.getItemId()){
            case R.id.about:
                fragmentClass = About.class;
                break;
            case R.id.umaual:
                fragmentClass = UserManual.class;
                break;
            case R.id.settings:
                fragmentClass = Settings.class;
                break;
            case R.id.restart:
                fragmentClass = Restart.class;
                break;
            default:
                fragmentClass = About.class;
        }
        try{
            myFragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        myDrawer.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;

            }
        });
    }
}
