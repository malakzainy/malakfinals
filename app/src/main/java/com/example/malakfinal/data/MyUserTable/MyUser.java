package com.example.malakfinal.data.MyUserTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * User Entity - يمثل جدول المستخدمين في قاعدة البيانات.
 * يحتوي على جميع المعلومات الضرورية للمريض أو الطبيب.
 */

@Entity(tableName = "users")
public class MyUser {

    // المفتاح الرئيسي - رقم المستخدم
    @PrimaryKey(autoGenerate = true)
    private int userId;

    // الاسم الكامل
    @ColumnInfo(name = "full_name")
    private String fullName;

    // البريد الإلكتروني (فريد)
    @ColumnInfo(name = "email")
    private String email;

    // كلمة المرور
    @ColumnInfo(name = "password")
    private String password;

    // نوع المستخدم: patient أو doctor
    @ColumnInfo(name = "role")
    private String role;

    // عمر المستخدم (للمرضى)
    @ColumnInfo(name = "age")
    private Integer age;

    // الجنس
    @ColumnInfo(name = "gender")
    private String gender;

    // نوع المرض (مثلاً: Asthma)
    @ColumnInfo(name = "disease_type")
    private String diseaseType;

    // الدواء المستخدم
    @ColumnInfo(name = "medication")
    private String medication;

    // رابط الصورة (اختياري)
    @ColumnInfo(name = "photo_url")
    private String photoUrl;

    // تاريخ إنشاء الحساب
    @ColumnInfo(name = "created_at")
    private String createdAt;

    // آخر تسجيل دخول
    @ColumnInfo(name = "last_login")
    private String lastLogin;


    // ✅ Constructor (ممكن تعمل أكثر من واحد)
    public MyUser(String fullName, String email, String password, String role, Integer age,
                String gender, String diseaseType, String medication,
                String photoUrl, String createdAt, String lastLogin) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.age = age;
        this.gender = gender;
        this.diseaseType = diseaseType;
        this.medication = medication;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    // ✅ Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDiseaseType() { return diseaseType; }
    public void setDiseaseType(String diseaseType) { this.diseaseType = diseaseType; }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
}
