package com.example.malakfinal.data.MyTask;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity //  يحدد أن هذه الفئة هي جدول في قاعدة البيانات
//@Entity ➜ تخبر النظام أن الكلاس  هو كيان (Entity).
//
//كل كائن (Object) من هذا الكلاس = صف (Row) في جدول.
//
//اسم الجدول يكون عادة نفس اسم الكلاس (User) ما لم تحدد اسمًا آخر.
//تحويل كلاس Java إلى جدول داخل قاعدة البيانات.
//@Id ➜ تحدد المفتاح الأساسي (Primary Key).
public class Plant {

    @PrimaryKey //مفتاح رئيسي مع قيم تلقائية
    @NonNull
    private String plantId;//

    private String title;// عنوان اسم النبتة
    private String description;// معطيات معلومات عن النبته
    private String image;//صورة لنبته

    public Plant(String title, String description) {
        // Default constructor required for calls to DataSnapshot.getValue(Plant.class)
        this.plantId = ""; // Initialize to non-null
        this.title = title;
        this.description = description;
    }

    @NonNull
    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(@NonNull String plantId) {
        this.plantId = plantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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