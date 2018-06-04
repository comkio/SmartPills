package upf.edu.smartpills;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, Pill.class, Treatment.class, TreatmentPill.class}, version = 3)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
