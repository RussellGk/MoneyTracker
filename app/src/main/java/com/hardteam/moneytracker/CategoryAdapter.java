package com.hardteam.moneytracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CardViewHolder>//ArrayAdapter<Category> {
{
    List<Category> category;

    public CategoryAdapter(List<Category> category) {
        this.category = category;
    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)    {
//
//        Category category = getItem(position);
//        if(convertView == null)
//        {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent,false);
//        }
//
//        RelativeLayout fone = (RelativeLayout) convertView.findViewById(R.id.category_items_list);
//
//        TextView name =(TextView) convertView.findViewById(R.id.name_text);
//
//        name.setText(category.getCategoryName());
//        return convertView;
//    }

    @Override
    public CategoryAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Category categories = category.get(position);
        holder.categories_name.setText(categories.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        protected TextView categories_name;

        public CardViewHolder(View convertView) {
            super(convertView);
            categories_name = (TextView) convertView.findViewById(R.id.name_text);
        }
    }
}
