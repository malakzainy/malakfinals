package com.example.malakfinal.data.MyTaskTable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plant {

    @PrimaryKey
    @NonNull
    private String plantId;

    private String title;
    private String description;
    private String image;

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
