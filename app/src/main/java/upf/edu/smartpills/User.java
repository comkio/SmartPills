package upf.edu.smartpills;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//Class equivalent to the table in the DataBase containing info about the user
@Entity
public class User {

    @NonNull
    @PrimaryKey
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
