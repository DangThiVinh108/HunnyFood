package com.example.doanhunnyfood.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.doanhunnyfood.entydi.Table;

import java.util.List;

@Dao
public interface TableDao {
    @Query("SELECT * FROM `table`")
    LiveData<List<Table>> findAll();
    @Insert
    void insert(Table table);

    @Update
    void update(Table table);
    @Query("SELECT COUNT(*) FROM `table`")
    int countTables();

}
