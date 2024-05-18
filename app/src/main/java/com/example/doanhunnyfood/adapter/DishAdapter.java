package com.example.doanhunnyfood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanhunnyfood.R;
import com.example.doanhunnyfood.entydi.Dish;
import com.example.doanhunnyfood.entydi.Table;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {
    private List<Dish> dishList;
    private static ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DishAdapter(List<Dish> dishList) {
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyc_dish_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        if (dishList != null){
            holder.imgDish.setImageResource(dishList.get(position).image);
            holder.txtName.setText(dishList.get(position).name);
            holder.txtPrice.setText(String.format("%.3f", dishList.get(position).price) + " đồng");
        }else holder.txtName.setText("No Dish");

    }

    @Override
    public int getItemCount() {
        if (dishList ==null)
            return 0;
        return dishList.size();
    }
    public Dish getItem(int position) {
        if (dishList != null && position >= 0 && position < dishList.size()) {
            return dishList.get(position);
        }
        return null;
    }
    public void setDishList(List<Dish> dishes){
        dishList = dishes;
        notifyDataSetChanged();

    }

    public class DishViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgDish;
        private TextView txtName, txtPrice;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDish = itemView.findViewById(R.id.imgDish);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        itemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
