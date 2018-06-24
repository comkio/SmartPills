package upf.edu.smartpills;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//Class equivalent to the table in the DataBase containing the relation between treatments and pills
//plus info about duration of the treatment, hour, repetition of each pill...
@Entity(foreignKeys =
        {@ForeignKey(entity = Pill.class, parentColumns = "id", childColumns = "pillId", onDelete = CASCADE),
                @ForeignKey(entity = Treatment.class, parentColumns = "id", childColumns = "treatmentId", onDelete = CASCADE)})
public class TreatmentPill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int pillId;

    private int treatmentId;

    private String from;

    private String fromHour;

    private String to;

    private String repetition;

    public TreatmentPill(int pillId, int treatmentId, String from, String fromHour, String to, String repetition) {
        this.pillId = pillId;
        this.treatmentId = treatmentId;
        this.from = from;
        this.fromHour = fromHour;
        this.to = to;
        this.repetition = repetition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPillId() {
        return pillId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRepetition() {
        return repetition;
    }

    public String getFromHour() {
        return fromHour;
    }

}
