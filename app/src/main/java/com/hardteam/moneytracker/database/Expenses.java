package com.hardteam.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RG on 16.12.2015.
 */

@Table(name = "Expenses")
public class Expenses extends Model{

    @Column(name ="price")
    public String price;

    @Column(name ="name")
    public String name;

    @Column(name ="date")
    public String date;

    @Column(name = "category", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Categories category;

    public Expenses()
    {
        super();
    }

    public Expenses(String price, String name, String date, Categories category)
    {
        super();
        this.price = price;
        this.name = name;
        this.date = date;
        this.category = category;
    }

}
