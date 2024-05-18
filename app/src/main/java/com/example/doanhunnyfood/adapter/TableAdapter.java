package com.example.doanhunnyfood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanhunnyfood.R;
import com.example.doanhunnyfood.entydi.Table;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private List<Table> tableList;
    public static ItemClickListener itemClickListener;
    public TableAdapter( List<Table> tableList) {
        this.tableList = tableList;

    }
    public Table getTableAtPosition(int position) {
        if (tableList != null && position >= 0 && position < tableList.size()) {
            return tableList.get(position);
        }
        return null;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyc_table_item, parent, false);

        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        if(tableList != null){
            holder.txtName.setText(tableList.get(position).name);
        }else {
            holder.txtName.setText("No Table");
        }
    }

    @Override
    public int getItemCount() {
        if (tableList == null)
            return 0;
        return tableList.size();
    }
    public void setTables(List<Table> tables) {
        tableList = tables;
        notifyDataSetChanged();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtTime;
        private ImageView imgTable, imgTime;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtTime = itemView.findViewById(R.id.txtTime);
            imgTable = itemView.findViewById(R.id.ic_table);
            imgTime = itemView.findViewById(R.id.ic_time);
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
