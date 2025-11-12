package com.example.malakfinal.data.MyUserTable;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * MyUserDao
 * واجهة تحتوي على عناوين الدوال + الاستعلامات الخاصة بجدول المستخدمين (users).
 */

@Dao
public interface MyUserQuery {

    // 🟩 إدخال مستخدم جديد إلى قاعدة البيانات
    @Insert
    void insertUser(MyUser user);

    // 🟨 تعديل بيانات مستخدم موجود
    @Update
    void updateUser(MyUser user);

    // 🟥 حذف مستخدم محدد
    @Delete
    void deleteUser(MyUser user);

    // 🔵 جلب جميع المستخدمين
    @Query("SELECT * FROM users")
    List<MyUser> getAllUsers();

    // 🟣 جلب مستخدم حسب رقم الـ ID
    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    MyUser getUserById(int id);

    // 🟠 تسجيل الدخول (تحقق من البريد وكلمة المرور)
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    MyUser login(String email, String password);

    // 🟢 البحث عن مستخدم حسب البريد الإلكتروني
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    MyUser getUserByEmail(String email);

    // ⚫ حذف جميع المستخدمين (لأغراض الاختبار مثلاً)
    @Query("DELETE FROM users")
    void deleteAllUsers();
}

