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
 //هي Annotation في Room (Android) وتعني:
//هذا الـ interface هو المسؤول عن تنفيذ العمليات على قاعدة البيانات.
//DAO اختصار لـ Data Access Object
//أي الكائن الذي يتعامل مع:
//الإدخال Insert
//التعديل Update
//الحذف Delete
//الاستعلام Query
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
    // استخراج
    @Query("SELECT * FROM asthma")
    List<AsthmaUser> getAllReports();

    // 🟣 جلب تقرير واحد حسب رقم الـ ID
    // SELECT * → اختار كل الأعمدة
    //FROM asthma → من جدول اسمه asthma
    //WHERE asthmaId = :id → اللي الـ asthmaId تبعه بساوي القيمة اللي بتمريرها
    //LIMIT 3 → رجّع أول 3 نتائج فقط
    @Query("SELECT * FROM asthma WHERE asthmaId = :id LIMIT 3")//استخراج جميع معطيات المستخدم الذي id مشابة asthmaId من الجدول
    AsthmaUser getReportById(int id);
    // ⚫ حذف كل التقارير (اختياري)
    @Query("DELETE FROM asthma")
    void deleteAllReports();
}
// @Override
//@Override هي Annotation (تعليمة توضيحية) بنحطها فوق دالة لما نكون عم نعيد تعريف (Override) دالة موجودة أصلاً في كلاس أب (Superclass) أو Interface.
//تتأكد إنك فعلاً عم تعيد تعريف دالة موجودة
//إذا غلطت باسم الدالة أو نوع البراميترات، المترجم (Compiler) بيعطيك خطأ.
//توضيح للكود
//أي مبرمج يشوفها يعرف إن هاي الدالة جاية من كلاس أب أو Interface.
//
// @NonNull
//هذا المتغير أو البراميتر أو القيمة الراجعة لازم ما تكون null.
//منع NullPointerException
//تساعد Android Studio يعطيك تحذير إذا حاولت تمرر null
//توضح للمبرمجين إن القيمة إلزامية
//@Query = الطريقة لكتابة أي استعلام SQL مخصص على قاعدة بيانات Room، وRoom يربط النتائج بالكائنات تلقائيًا.