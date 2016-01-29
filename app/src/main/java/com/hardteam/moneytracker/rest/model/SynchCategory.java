package com.hardteam.moneytracker.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 22.01.2016.
 */
public class SynchCategory {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Data> data = new ArrayList<Data>();

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Data> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Data> data) {
        this.data = data;
    }
}
