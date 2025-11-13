package com.example.malakfinal.data.AppDataBaseT;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.malakfinal.data.MyAsthmaTable.Asthma;
import com.example.malakfinal.data.MyAsthmaTable.AsthmaQuery;
import com.example.malakfinal.data.MyTaskTable.MyTask;
import com.example.malakfinal.data.MyTaskTable.MyTaskQuery;
import com.example.malakfinal.data.MyUserTable.MyUser;
import com.example.malakfinal.data.MyUserTable.MyUserQuery;

@Database(entities = {MyUser.class, Asthma.class, MyTask.class}, version = 1)
/**
 * الفئة المسؤولة عن بناء قاعدة البيانات بجدوالها
 * وتوفر لنا كائن للتعامل مع قاعدة البيانات
 */
    public abstract class AppDataBase extends RoomDatabase {

        /**
         * كائن للتعامل مع قاعدة البيانات (Singleton)
         */
        private static AppDataBase db;

        /**
         * يعيد كائن العمليات الخاصة بجدول المستخدمين
         * @return
         */
        public abstract MyUserQuery getMyUserQuery();

        /**
         * يعيد كائن العمليات الخاصة بجدول الربو
         * @return
         */
        public abstract AsthmaQuery getAsthmaQuery();

        /**
         * يعيد كائن العمليات الخاصة بجدول المهمات
         * @return
         */
        public abstract MyTaskQuery getMyTaskQuery();

        /**
         * بناء قاعدة البيانات وإعادة كائن يشير إليها
         * @param context
         * @return
         */
        public static AppDataBase getDB(Context context) {
            if (db == null) {
                db = Room.databaseBuilder(
                                context,
                                AppDataBase.class,
                                "asthma_app_db" // اسم قاعدة البيانات
                        )
                        .fallbackToDestructiveMigration() // إعادة بناء القاعدة عند التغيير
                        .allowMainThreadQueries() // للسماح بالعمليات على Main Thread (يفضل استخدام Async لاحقًا)
                        .build();
            }
            return db;
        }
    }

