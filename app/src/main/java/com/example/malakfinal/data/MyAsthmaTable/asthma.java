package com.example.malakfinal.data.MyAsthmaTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Asthma Entity - يمثل جدول معلومات الربو للمريض.
 */

@Entity(tableName = "asthma")
public class asthma {

    // المفتاح الرئيسي
    @PrimaryKey(autoGenerate = true)
    private int asthmaId;

    // معرف المريض (ربطه بجدول User)
    @ColumnInfo(name = "user_id")
    private int userId;

    // تاريخ التشخيص
    @ColumnInfo(name = "diagnosis_date")
    private String diagnosisDate;

    // نوع الربو (مثل: allergic, exercise-induced, chronic)
    @ColumnInfo(name = "asthma_type")
    private String asthmaType;

    // مستوى الخطورة (mild, moderate, severe)
    @ColumnInfo(name = "severity")
    private String severity;

    // الأعراض الشائعة (sneezing, cough, wheezing, shortness of breath)
    @ColumnInfo(name = "symptoms")
    private String symptoms;

    // أسماء الأدوية المستخدمة
    @ColumnInfo(name = "medications")
    private String medications;

    // تكرار الهجمات خلال الأسبوع
    @ColumnInfo(name = "attack_frequency_weekly")
    private int attackFrequencyWeekly;

    // ملاحظات إضافية من الطبيب
    @ColumnInfo(name = "doctor_notes")
    private String doctorNotes;

    // تاريخ آخر تحديث للسجل
    @ColumnInfo(name = "last_updated")
    private String lastUpdated;

    // ✅ Constructor
    public asthma(int userId, String diagnosisDate, String asthmaType, String severity,
                  String symptoms, String medications, int attackFrequencyWeekly,
                  String doctorNotes, String lastUpdated) {
        this.userId = userId;
        this.diagnosisDate = diagnosisDate;
        this.asthmaType = asthmaType;
        this.severity = severity;
        this.symptoms = symptoms;
        this.medications = medications;
        this.attackFrequencyWeekly = attackFrequencyWeekly;
        this.doctorNotes = doctorNotes;
        this.lastUpdated = lastUpdated;
    }

    // ✅ Getters and Setters
    public int getAsthmaId() { return asthmaId; }
    public void setAsthmaId(int asthmaId) { this.asthmaId = asthmaId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getDiagnosisDate() { return diagnosisDate; }
    public void setDiagnosisDate(String diagnosisDate) { this.diagnosisDate = diagnosisDate; }

    public String getAsthmaType() { return asthmaType; }
    public void setAsthmaType(String asthmaType) { this.asthmaType = asthmaType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getMedications() { return medications; }
    public void setMedications(String medications) { this.medications = medications; }

    public int getAttackFrequencyWeekly() { return attackFrequencyWeekly; }
    public void setAttackFrequencyWeekly(int attackFrequencyWeekly) { this.attackFrequencyWeekly = attackFrequencyWeekly; }

    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}
