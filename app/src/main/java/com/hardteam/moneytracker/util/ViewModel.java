package com.hardteam.moneytracker.util;

/**
 * Created by RG on 12.02.2016.
 */
public class ViewModel implements Comparable {

    public String name;
    public float sum;

    public ViewModel(String name, float sum)
    {
        this.name = name;
        this.sum = sum;
    }

    @Override
    public int compareTo(Object vmodel) {
        ViewModel tmp = (ViewModel)vmodel;
        if (this.sum < tmp.sum)
        {
            return -1;
        }
        else if(this.sum > tmp.sum)
        {
            return 1;
        }
        return 0;
    }
}
