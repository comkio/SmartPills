package upf.edu.smartpills;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAppDatabase db = Room.databaseBuilder(getApplicationContext(),
                MyAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

}
