package com.hardteam.moneytracker.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rg on 12/29/15.
 */
public class Data {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("id")
    @Expose
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
