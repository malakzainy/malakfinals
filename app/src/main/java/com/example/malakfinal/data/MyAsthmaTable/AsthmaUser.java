package com.example.malakfinal.data.MyAsthmaTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Asthma Entity - يمثل جدول معلومات الربو للمريض.
 */
@Entity(tableName = "asthma")
public class AsthmaUser {

    // المفتاح الرئيسي
    @PrimaryKey(autoGenerate = true)
    private String asthmaId;

    // معرف المريض (ربطه بجدول User)
    private String userId;

    // تاريخ التشخيص
    private String diagnosisDate;

    // نوع الربو (مثل: allergic, exercise-induced, chronic)
    private String asthmaType;

    // مستوى الخطورة (mild, moderate, severe)
    private String severity;

    // الأعراض الشائعة (sneezing, cough, wheezing, shortness of breath)
    private String symptoms;

    // أسماء الأدوية المستخدمة
    private String medications;

    // تكرار الهجمات خلال الأسبوع
    private int attackFrequencyWeekly;

    // ملاحظات إضافية من الطبيب
    private String doctorNotes;

    // تاريخ آخر تحديث للسجل
    private String lastUpdated;

    // Constructor for Room
    public AsthmaUser() {}



    // Getters and Setters
    public String getAsthmaId() {
        return asthmaId;
    }

    public void setAsthmaId(String asthmaId) {
        this.asthmaId = asthmaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getAsthmaType() {
        return asthmaType;
    }

    public void setAsthmaType(String asthmaType) {
        this.asthmaType = asthmaType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public int getAttackFrequencyWeekly() {
        return attackFrequencyWeekly;
    }

    public void setAttackFrequencyWeekly(int attackFrequencyWeekly) {
        this.attackFrequencyWeekly = attackFrequencyWeekly;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String containToString() {
        return "asthma{" +
                "userId=" + userId +
                ", diagnosisDate='" + diagnosisDate + '\'' +
                ", asthmaType='" + asthmaType + '\'' +
                ", severity='" + severity + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", medications='" + medications + '\'' +
                ", attackFrequencyWeekly=" + attackFrequencyWeekly +
                ", doctorNotes='" + doctorNotes + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}
