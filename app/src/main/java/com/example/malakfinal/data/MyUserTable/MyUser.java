package com.example.malakfinal.data.MyUserTable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class MyUser {

    @PrimaryKey
    @NonNull
    private String userId;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "email")
    private String email;// الايميل

    private String password;// الباسوورد السيسما
    private String role;// الدور
    private Integer age;//العمر
    private String gender;// الجنس
    private String diseaseType;//نوع المرض
    private String medication;//الدواء
    private String photoUrl;//رابط لصورة
    private String createdAt;// تم انشاءه في
    private String lastLogin;//اخر دخول

    // Constructor for Room
    public MyUser() {
        this.userId = ""; // Primary key cannot be null
    }

    // Constructor for creating a new user during sign-up
    public MyUser(@NonNull String userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
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