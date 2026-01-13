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
public interface AsthmaUserQuery {

    // 🟩 إضافة تقرير ربو جديد
    @Insert
    void insertAsthmaReport(AsthmaUser asthma);

    // 🟨 تعديل تقرير ربو موجود
    @Update
    void updateAsthmaReport(AsthmaUser asthma);

    // 🟥 حذف تقرير محدد
    @Delete
    void deleteAsthmaReport(AsthmaUser asthma);

    // 🔵 جلب كل تقارير الربو
    @Query("SELECT * FROM asthma")
    List<AsthmaUser> getAllReports();

    // 🟣 جلب تقرير واحد حسب رقم الـ ID
    @Query("SELECT * FROM asthma WHERE asthmaId = :id LIMIT 1")
    AsthmaUser getReportById(int id);
    // ⚫ حذف كل التقارير (اختياري)
    @Query("DELETE FROM asthma")
    void deleteAllReports();
}
