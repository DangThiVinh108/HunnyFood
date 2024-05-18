package com.example.doanhunnyfood.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.doanhunnyfood.entydi.Dish;

import java.util.List;

@Dao
public interface DishDao {

    @Query("SELECT * FROM `dish`")
    LiveData<List<Dish>> FindAll();

    @Insert
    void insert(Dish dish);

    @Update
    void update(Dish dish);
}
