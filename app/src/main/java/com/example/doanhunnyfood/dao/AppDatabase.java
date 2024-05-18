package com.example.doanhunnyfood.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.text.PrecomputedText;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.doanhunnyfood.R;
import com.example.doanhunnyfood.entydi.Dish;
import com.example.doanhunnyfood.entydi.Table;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.Result;

@Database(entities = {Table.class, Dish.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TableDao tableDao();

    public abstract DishDao dishDao();

    private static volatile AppDatabase INSTANCE;

    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);


    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() ->{
                populateInitiaData(INSTANCE.tableDao());

                populateInitialDishData(INSTANCE.dishDao());
            });
        }
    };

    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "hunny_food_store")
                        .fallbackToDestructiveMigration()
                        .addCallback(callback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static void populateInitiaData(TableDao tableDao) {

        String[] tables = new String[]{"ban 1", "ban 2", "ban 3", "ban 4", "ban 5"};
        for (String name : tables) {
            Table table = new Table();
            table.name = name;
            tableDao.insert(table);
        }
    }
    private static void populateInitialDishData(DishDao dishDao) {
        Dish[] dishes = new Dish[]{
                new Dish("Pho", "Vietnamese noodle soup", 50.0, R.drawable.img_pho, 1),
                new Dish("Banh Mi", "Vietnamese sandwich", 20.0, R.drawable.img_banh_mi, 1),
                new Dish("bun cha", "Fresh spring rolls", 30.0, R.drawable.img_bun_cha, 1),
                new Dish("Coffee", "Vietnamese coffee", 15.0, R.drawable.img_coffee, 1),
                new Dish("Tea", "Vietnamese tea", 10.0, R.drawable.img_tea, 1)
        };
        for (Dish dish : dishes) {
            dishDao.insert(dish);
        }
    }
}
