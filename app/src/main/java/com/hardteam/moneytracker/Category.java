package com.hardteam.moneytracker;

/**
 * Created by RG on 07.12.2015.
 */
public class Category {

    private String categoryName;

    Category(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
