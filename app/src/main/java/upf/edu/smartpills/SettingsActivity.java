package upf.edu.smartpills;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    //Class to allow the user to change some of the setting such as language or contact the developers
    private int PERMISSION_CODE = 1;
    Button languages, tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Settings");
        loadLocale();
        setContentView(R.layout.activity_settings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        languages = findViewById(R.id.button3);

        //On click allow the user to select a language
        languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
                requestCallPermission();
            }
        });

        tw = findViewById(R.id.button2);

        //Go to the developers website on click (Goes to Twitter as an example)
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://twitter.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    //Method that shows a dialog to allow the user to choose a language between English and Catalan
    private void showChangeLanguageDialog() {
        final String[] listItem = {"English", "Catalan"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(SettingsActivity.this);
        mbuilder.setTitle("Choose Language...");
        mbuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("En");
                    recreate();
                }
                if (which == 1) {
                    setLocale("Ca");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mbuilder.create();
        mDialog.show();

    }

    //Change the language selected by the user
    private void setLocale(String language) {
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    //Load current language
    private void loadLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languages = sharedPreferences.getString("My_Lang", "");
        setLocale(languages);
    }

    //On click phone a support number (One of the developers in our case)
    public void onClickLlamada(View v) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:666477346"));
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {

        }


    }

    //Request permission to perform a phone call
    private void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this).setTitle("Permission Needed").
                    setMessage("The permission is needed to call SmartPills offices").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        }

    }

    //Show a Toast informing of the call permision
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Go to the Calendar on back pressed
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(SettingsActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
}


