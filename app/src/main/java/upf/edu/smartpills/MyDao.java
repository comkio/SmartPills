package upf.edu.smartpills;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM pill")
    List<Pill> getAllPills();

    @Query("SELECT * FROM treatment")
    List<Treatment> getAllTreatments();

    @Query("SELECT * FROM treatmentpill")
    List<TreatmentPill> getAllTreatmentPills();
    
    @Query("SELECT * FROM TreatmentPill WHERE treatmentId LIKE (:treatmentId)")
    List<TreatmentPill> getTreatmentPills(int treatmentId);

    @Query("SELECT * FROM Pill WHERE id LIKE (:pillId)")
    Pill getPillbyId(int pillId);

    @Query("SELECT pillId FROM TreatmentPill WHERE treatmentId LIKE (:treatmentId)")
    List<Integer> getPillsForTreatment(int treatmentId);

    @Insert
    void insertUsers(User... users);

    @Insert
    void insertPills(Pill... pills);

    @Insert
    void insertTreatments(Treatment... treatments);

    @Delete
    void delete(User user);

    @Query("SELECT COUNT(*) FROM user ")
    int countUsers();

    //pildora por dia
}
