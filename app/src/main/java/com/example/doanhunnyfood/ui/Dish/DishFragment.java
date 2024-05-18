package com.example.doanhunnyfood.ui.Dish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanhunnyfood.R;
import com.example.doanhunnyfood.adapter.Cart;
import com.example.doanhunnyfood.adapter.CartAdapter;
import com.example.doanhunnyfood.adapter.DishAdapter;
import com.example.doanhunnyfood.adapter.ItemClickListener;
import com.example.doanhunnyfood.entydi.Dish;
import com.example.doanhunnyfood.entydi.Table;
import com.example.doanhunnyfood.ui.DinnerTable.OnTableSelectedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DishFragment extends Fragment  {
    private RecyclerView recyclerViewCart, recyclerViewDish;
    private Button btnXN, btnHuy;
    private TextView txtNameTable;
    private DishAdapter dishAdapter;
    private Table mTable;
    private CartAdapter cartAdapter;
    private List<Cart> listCat;
    private DishViewModel mViewModel;

    public static DishFragment newInstance(Table table) {
        DishFragment fragment = new DishFragment();
        Bundle args = new Bundle();
        args.putParcelable("TABLE", table);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewDish = view.findViewById(R.id.recyclerViewDish);
        txtNameTable = view.findViewById(R.id.txtNameTable);
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewDish.setLayoutManager(layoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCart.setLayoutManager(linearLayoutManager);

        listCat = new ArrayList<>();

        dishAdapter = new DishAdapter(null);
        recyclerViewDish.setAdapter(dishAdapter);

        if (getArguments() != null) {
            mTable =  getArguments().getParcelable("TABLE");
            // Sử dụng dữ liệu bàn ở đây
            if (mTable != null) {
                txtNameTable.setText(mTable.name);
            }
        }
        dishAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Dish posDish = dishAdapter.getItem(position);
                if (listCat.size() > 0){
                    boolean flag = false;
                    for (int i = 0; i<listCat.size(); i++){
                        if (listCat.get(i).getId() == posDish.id){
                            listCat.get(i).setQtt(listCat.get(i).getQtt() + 1);
                            flag = true;
                        }
                    }
                    if (flag == false){
                        Cart cart = new Cart();
                        cart.setId(posDish.id);
                        cart.setImg(posDish.image);
                        cart.setQtt(1);
                        cart.setPrice(posDish.price);
                        cart.setName(posDish.name);
                        listCat.add(cart);
                    }

                }else {
                    Cart cart = new Cart();
                    cart.setId(posDish.id);
                    cart.setImg(posDish.image);
                    cart.setQtt(1);
                    cart.setPrice(posDish.price);
                    cart.setName(posDish.name);
                    listCat.add(cart);
                }

                if (cartAdapter == null) {
                    cartAdapter = new CartAdapter(listCat);
                    recyclerViewCart.setAdapter(cartAdapter);
                } else {
                    cartAdapter.setCartList(listCat);
                    cartAdapter.notifyDataSetChanged(); // Hoặc có thể sử dụng notifyDataSetChanged() để thông báo sự thay đổi
                }
            }
        });


        mViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        mViewModel.getAllDish().observe(getViewLifecycleOwner(), new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishList) {
                dishAdapter.setDishList(dishList);
            }
        });
    }


}