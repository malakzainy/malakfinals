package com.example.malakfinal.data.MyTaskTable;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * MyTask Entity - يمثل جدول المهمات للمستخدمين.
 */

@Entity
/**
 * كلاس Plant يمثل كائن نبات داخل التطبيق.
 * <p>
 * يحتوي هذا الكلاس على جميع المعلومات الخاصة بالنبات
 * مثل العنوان، الوصف، والصورة، إضافة إلى رقم تعريفي فريد.
 */
public class Plant {

    /**
     * رقم تعريفي فريد لكل نبات.
     * يتم توليده تلقائيًا من قاعدة البيانات.
     */
    @PrimaryKey(autoGenerate = true)
    private int plantId;

    /** اسم أو عنوان النبات */
    private String title;

    /** وصف النبات */
    private String description;

    /** مسار أو رابط صورة النبات */
    private String image;

    /**
     * إرجاع رقم تعريف النبات.
     *
     * @return رقم تعريف النبات
     */
    public int getPlantId() {
        return plantId;
    }

    /**
     * تعيين رقم تعريف النبات.
     *
     * @param plantId رقم تعريف النبات
     */
    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    /**
     * إرجاع عنوان النبات.
     *
     * @return عنوان النبات
     */
    public String getTitle() {
        return title;
    }

    public String FireBaseId() {
        return null;
    }

    /**
     * تعيين عنوان النبات.
     *
     * @param title عنوان النبات
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * إرجاع وصف النبات.
     *
     * @return وصف النبات
     */
    public String getDescription() {
        return description;
    }

    /**
     * تعيين وصف النبات.
     *
     * @param description وصف النبات
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * إرجاع مسار أو رابط صورة النبات.
     *
     * @return صورة النبات
     */
    public String getImage() {
        return image;
    }

    /**
     * تعيين مسار أو رابط صورة النبات.
     *
     * @param image صورة النبات
     */
    public void setImage(String image) {
        this.image = image;
    }
}
