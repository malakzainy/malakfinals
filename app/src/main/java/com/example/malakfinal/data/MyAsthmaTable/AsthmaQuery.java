package com.example.malakfinal.data.MyAsthmaTable;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

/**
 * AsthmaDao
 * واجهة تحتوي على استعلامات جدول بيانات مرضى الربو.
 */

@Dao
public interface AsthmaQuery {

    // 🟩 إضافة تقرير ربو جديد
    @Insert
    void insertAsthmaReport(Asthma asthma);

    // 🟨 تعديل تقرير ربو موجود
    @Update
    void updateAsthmaReport(Asthma asthma);

    // 🟥 حذف تقرير محدد
    @Delete
    void deleteAsthmaReport(Asthma asthma);

    // 🔵 جلب كل تقارير الربو
    @Query("SELECT * FROM Asthma")
    List<Asthma> getAllReports();

    // 🟣 جلب تقرير واحد حسب رقم الـ ID
    @Query("SELECT * FROM Asthma WHERE asthmaId = :id LIMIT 1")
    Asthma getReportById(int id);
    // ⚫ حذف كل التقارير (اختياري)
    @Query("DELETE FROM Asthma")
    void deleteAllReports();
}
