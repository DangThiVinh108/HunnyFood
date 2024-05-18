package com.example.doanhunnyfood.ui.Dish;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.doanhunnyfood.adapter.Cart;
import com.example.doanhunnyfood.entydi.Dish;
import com.example.doanhunnyfood.repository.DishRepository;

import java.util.List;

public class DishViewModel extends AndroidViewModel {

    private DishRepository dishRepository;
    private LiveData<List<Dish>> mAllDish;

    private LiveData<List<Cart>> mAllCart;
    public DishViewModel(@NonNull Application application) {
        super(application);
        dishRepository = new DishRepository(application);
        mAllDish = dishRepository.getAllDish();
        mAllCart = dishRepository.getAllCart();
    }

    public LiveData<List<Cart>> getAllCart() {
        return mAllCart;
    }

    public void addToCart(Cart cart) {
        dishRepository.addToCart(cart);
    }

    public void removeFromCart(Cart cart) {
        dishRepository.removeFromCart(cart);
    }

    public LiveData<List<Dish>> getAllDish() {
        return mAllDish;
    }

    public void insert(Dish dish){
        dishRepository.insert(dish);
    }
}