package com.example.doanhunnyfood.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doanhunnyfood.adapter.Cart;
import com.example.doanhunnyfood.dao.AppDatabase;
import com.example.doanhunnyfood.dao.DishDao;
import com.example.doanhunnyfood.entydi.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DishRepository {
    private DishDao mDishDao;

    private LiveData<List<Dish>> AllDish;

    private MutableLiveData<List<Cart>> AllCart;

    ExecutorService executorService;


    public DishRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.mDishDao = db.dishDao();
        this.AllDish = mDishDao.FindAll();
        this.AllCart = new MutableLiveData<>(new ArrayList<>());
        this.executorService = Executors.newFixedThreadPool(4);
    }
    public LiveData<List<Cart>> getAllCart() {
        return AllCart;
    }
    public void addToCart(Cart cart) {
        List<Cart> currentCart = AllCart.getValue();
        if (currentCart != null) {
            currentCart.add(cart);
            AllCart.setValue(currentCart);
        }
    }

    public void removeFromCart(Cart cart) {
        List<Cart> currentCart = AllCart.getValue();
        if (currentCart != null) {
            currentCart.remove(cart);
            AllCart.setValue(currentCart);
        }
    }
    public LiveData<List<Dish>> getAllDish() {
        return AllDish;
    }

    public void insert(Dish dish){
        executorService.execute(() ->mDishDao.insert(dish));
    }
}
