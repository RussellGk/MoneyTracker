package com.hardteam.moneytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static android.graphics.Color.rgb;

/**
 * Created by RG on 07.12.2015.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    List<Category> category;
    static int foneColorTwo;

    public CategoryAdapter(Context context, List<Category> category) {
        super(context,0,category);
        this.category = category;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)    {

        Category category = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent,false);
        }

        RelativeLayout fone = (RelativeLayout) convertView.findViewById(R.id.category_items_list);
        foneColorTwo = rgb(getIntColor(),getIntColor(),getIntColor());
        fone.setBackgroundColor(foneColorTwo);

        TextView name =(TextView) convertView.findViewById(R.id.name_text);

        name.setText(category.getCategoryName());
        return convertView;
    }

    private static int getIntColor()
    {
        int rnd = 0 + (int)(Math.random() * ((255 - 0) + 1));

        return rnd;
    }
}
