package com.example.malakfinal.data.MyTask;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

/**
 * MyTaskDao
 * واجهة تحتوي على الاستعلامات الخاصة بجدول المهمات (tasks).
 */

@Dao
public interface MyPlantQuery {

    // 🟩 إضافة مهمة جديدة
    @Insert
    void insertPlant(Plant task);

    // 🟨 تعديل مهمة موجودة
    @Update
    void updateTask(Plant task);

    // 🟥 حذف مهمة
    @Delete
    void deleteTask(Plant task);

    // 🔵 جلب كل المهمات
    @Query("SELECT * FROM Plant")
    List<Plant> getAllPlants();

    // 🟣 جلب مهمة حسب رقم الـ ID
    @Query("SELECT * FROM Plant WHERE plantId = :id LIMIT 1")
    Plant getTaskById(int id);

    // ⚫ حذف جميع المهمات (اختياري)
    @Query("DELETE FROM Plant")
    void deleteAllTasks();
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
//
