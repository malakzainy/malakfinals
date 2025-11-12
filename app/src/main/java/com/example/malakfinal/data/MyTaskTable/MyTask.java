package com.example.malakfinal.data.MyTaskTable;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * MyTask Entity - يمثل جدول المهمات للمستخدمين.
 */

@Entity(tableName = "tasks")
public class MyTask {

    @PrimaryKey(autoGenerate = true)
    private int taskId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "due_date")
    private String dueDate;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "last_updated")
    private String lastUpdated;

    // ✅ Constructor
    public MyTask(int userId, String title, String description, boolean isCompleted,
                  String dueDate, String createdAt, String lastUpdated) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    // ✅ Getters & Setters
    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    // ✅ toString()
    @Override
    public String toString() {
        return "MyTask{" +
                "taskId=" + taskId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", dueDate='" + dueDate + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}
