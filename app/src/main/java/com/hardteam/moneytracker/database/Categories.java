package com.hardteam.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by RG on 17.12.2015.
 */

@Table(name = "categories")
public class Categories extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "catid")
    public int catid;

    public Categories()
    {
        super();
    }

    public Categories(String name, int catid)
    {
        super();
        this.name = name;
        this.catid = catid;
    }

    public List<Expenses> expenses()
    {
        return getMany(Expenses.class, "category");
    }

    @Override
    public String toString() {
        return name;
    }
}


