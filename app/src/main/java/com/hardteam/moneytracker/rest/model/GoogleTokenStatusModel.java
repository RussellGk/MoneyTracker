package com.hardteam.moneytracker.rest.model;

import com.google.gson.annotations.Expose;

/**
 * Created by RG on 27.01.2016.
 */
public class GoogleTokenStatusModel {

    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
