package com.example.malakfinal.data.MyTaskTable;


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
    void insertTask(Plant task);

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

