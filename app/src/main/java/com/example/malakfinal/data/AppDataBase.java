package com.example.malakfinal.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.malakfinal.data.MyAsthmaTable.AsthmaUser;
import com.example.malakfinal.data.MyAsthmaTable.AsthmaUserQuery;
import com.example.malakfinal.data.MyTaskTable.MyPlantQuery;
import com.example.malakfinal.data.MyTaskTable.Plant;
import com.example.malakfinal.data.MyUserTable.MyUser;
import com.example.malakfinal.data.MyUserTable.MyUserQuery;

@Database(entities = {MyUser.class, AsthmaUser.class, Plant.class}, version = 2, exportSchema = false)
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
        public abstract AsthmaUserQuery getAsthmaQuery();

        /**
         * يعيد كائن العمليات الخاصة بجدول المهمات
         * @return
         */
        public abstract MyPlantQuery getMyPlantQuery();

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


