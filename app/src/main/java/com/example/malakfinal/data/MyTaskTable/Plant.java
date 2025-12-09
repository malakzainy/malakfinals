package com.example.malakfinal.data.MyTaskTable;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * MyTask Entity - يمثل جدول المهمات للمستخدمين.
 */

@Entity
public class Plant {

    @PrimaryKey(autoGenerate = true)
    private int plantId;


    private String title;

    private String description;
    private String image;



    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
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
