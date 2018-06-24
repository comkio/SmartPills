package upf.edu.smartpills;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//Class equivalent to the table in the DataBase containing info about each pill
@Entity
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String quantity;

    public Pill(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

}
