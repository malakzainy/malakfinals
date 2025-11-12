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
public interface MyTaskQuery {

    // 🟩 إضافة مهمة جديدة
    @Insert
    void insertTask(MyTask task);

    // 🟨 تعديل مهمة موجودة
    @Update
    void updateTask(MyTask task);

    // 🟥 حذف مهمة
    @Delete
    void deleteTask(MyTask task);

    // 🔵 جلب كل المهمات
    @Query("SELECT * FROM tasks")
    List<MyTask> getAllTasks();

    // 🟣 جلب مهمة حسب رقم الـ ID
    @Query("SELECT * FROM tasks WHERE taskId = :id LIMIT 1")
    MyTask getTaskById(int id);

    // 🟠 جلب جميع المهمات لمستخدم معين
    @Query("SELECT * FROM tasks WHERE user_id = :userId")
    List<MyTask> getTasksByUserId(int userId);

    // 🟢 جلب المهمات المكتملة لمستخدم معين
    @Query("SELECT * FROM tasks WHERE user_id = :userId AND is_completed = 1")
    List<MyTask> getCompletedTasksByUserId(int userId);

    // 🔴 جلب المهمات غير المكتملة لمستخدم معين
    @Query("SELECT * FROM tasks WHERE user_id = :userId AND is_completed = 0")
    List<MyTask> getPendingTasksByUserId(int userId);

    // ⚫ حذف جميع المهمات (اختياري)
    @Query("DELETE FROM tasks")
    void deleteAllTasks();
}

