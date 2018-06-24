package upf.edu.smartpills;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//DAO with the queries necessary to interact with our DataBase and the different tables.
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
    List<TreatmentPill> getTreatmentPillByTreatmentId(int treatmentId);

    @Query("SELECT * FROM TreatmentPill WHERE pillId LIKE (:pillId)")
    TreatmentPill getTreatmentPillByPillId(int pillId);

    @Query("SELECT * FROM Pill WHERE id LIKE (:pillId)")
    Pill getPillbyId(int pillId);


    @Insert
    void insertUsers(User... users);

    @Insert
    void insertPills(List<Pill> pills);

    @Insert
    void insertTreatments(Treatment... treatments);

    @Insert
    void insertTreatmentPills(TreatmentPill... treatmentPill);


    @Delete
    void deleteTreatment(Treatment treatment);

    @Delete
    void deletePill(Pill pill);


    @Query("DELETE FROM user")
    void resetUser();

    @Query("DELETE FROM treatment")
    void resetTreatment();

    @Query("DELETE FROM pill")
    void resetPill();

    @Query("DELETE FROM treatmentpill")
    void resetTreatmentPill();

    @Query("SELECT COUNT(*) FROM user ")
    int countUsers();

    @Query("SELECT COUNT(*) FROM treatment ")
    int countTreatments();

}
