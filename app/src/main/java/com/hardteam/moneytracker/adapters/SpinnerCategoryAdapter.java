package com.hardteam.moneytracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;

import java.util.List;

/**
 * Created by RG on 19.12.2015.
 */

public class SpinnerCategoryAdapter extends BaseAdapter implements SpinnerAdapter{

    List<Categories> categories;

    public SpinnerCategoryAdapter( List<Categories> categories)
    {
        this.categories = categories;
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Categories getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Categories category = getItem(position);
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_categories, parent, false);

        TextView text_item_spinner = (TextView) convertView.findViewById(R.id.spinner_category_layout);
        text_item_spinner.setText(category.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        Categories category = getItem(position);
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_categories, parent, false);

        TextView text_item_spinner = (TextView) convertView.findViewById(R.id.spinner_category_layout);
        text_item_spinner.setText(category.name);

        return convertView;
    }
}
