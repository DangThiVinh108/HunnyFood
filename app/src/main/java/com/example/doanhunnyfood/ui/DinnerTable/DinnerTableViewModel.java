package com.example.doanhunnyfood.ui.DinnerTable;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanhunnyfood.entydi.Table;
import com.example.doanhunnyfood.repository.TableRepository;

import java.util.List;

public class DinnerTableViewModel extends AndroidViewModel {

    private TableRepository mTableRepository;
    private LiveData<List<Table>> mAllTable;
    public DinnerTableViewModel(@NonNull Application application) {
        super(application);
        mTableRepository = new TableRepository(application);
        mAllTable = mTableRepository.getAllTable();
    }

    public LiveData<List<Table>> getAllTable() {
        return mAllTable;
    }
    public void insert(Table table){
        mTableRepository.insert(table);
    }
}