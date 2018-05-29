package upf.edu.smartpills;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys =
        {@ForeignKey(entity = Pill.class, parentColumns = "id", childColumns = "pillId"),
                @ForeignKey(entity = Treatment.class, parentColumns = "id", childColumns = "treatmentId")})

public class TreatmentPill {

    @PrimaryKey
    int id;

    int pillId;

    int treatmentId;

    String from;

    int fromHour;

    String to;

    int repetition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPillId() {
        return pillId;
    }

    public void setPillId(int pillId) {
        this.pillId = pillId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
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

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }
}
