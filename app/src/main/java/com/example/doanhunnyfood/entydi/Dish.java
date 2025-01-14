package com.example.doanhunnyfood.entydi;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dish {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "image")
    public int image;

    @ColumnInfo(name = "status")
    public int status;

    public Dish(String name, String description, double price, int image, int status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.status = status;
    }

}
