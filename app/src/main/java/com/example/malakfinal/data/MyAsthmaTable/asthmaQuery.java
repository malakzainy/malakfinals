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
public interface asthmaQuery {

    // 🟩 إضافة تقرير ربو جديد
    @Insert
    void insertAsthmaReport(asthma asthma);

    // 🟨 تعديل تقرير ربو موجود
    @Update
    void updateAsthmaReport(asthma asthma);

    // 🟥 حذف تقرير محدد
    @Delete
    void deleteAsthmaReport(asthma asthma);

    // 🔵 جلب كل تقارير الربو
    @Query("SELECT * FROM asthma")
    List<asthma> getAllReports();

    // 🟣 جلب تقرير واحد حسب رقم الـ ID
    @Query("SELECT * FROM asthma WHERE asthmaId = :id LIMIT 1")
    asthma getReportById(int id);

    // 🟠 جلب جميع التقارير حسب رقم المستخدم (userId)
    @Query("SELECT * FROM asthma WHERE user_id = :userId")
    List<asthma> getReportsByUserId(int userId);

    // 🟢 جلب آخر تقرير مضاف لمستخدم معيّن
    @Query("SELECT * FROM asthma WHERE user_id = :userId ORDER BY last_updated DESC LIMIT 1")
    asthma getLatestReportByUserId(int userId);

    // ⚫ حذف كل التقارير (اختياري)
    @Query("DELETE FROM asthma")
    void deleteAllReports();
}
