package upf.edu.smartpills;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

//Class to declare the DataBase an its entities.
@Database(entities = {User.class, Pill.class, Treatment.class, TreatmentPill.class}, version = 3)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
