package upf.edu.smartpills;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//Class equivalent to the table in the DataBase containing info about each treatment
@Entity
public class Treatment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String treatmentName;

    public Treatment(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

}

