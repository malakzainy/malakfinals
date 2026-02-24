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

    @Insert //   اضافة سطر جديد(Row) او مجموعة اسطر الى الجدول المحدد في قاعدة البيانات
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
    // SELECT * → اختار كل الأعمدة
    //FROM asthma → من جدول اسمه asthma
    //WHERE asthmaId = :id → اللي الـ asthmaId تبعه بساوي القيمة اللي بتمريرها
    //LIMIT 3 → رجّع أول 3 نتائج فقط
    @Query("SELECT * FROM asthma WHERE asthmaId = :id LIMIT 3")
    AsthmaUser getReportById(int id);
    // ⚫ حذف كل التقارير (اختياري)
    @Query("DELETE FROM asthma")
    void deleteAllReports();
}
