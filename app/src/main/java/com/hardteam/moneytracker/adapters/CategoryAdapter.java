package com.hardteam.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardteam.moneytracker.Category;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;

import java.util.List;

/**
 * Created by RG on 07.12.2015.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CardViewHolder>
{
    List<Categories> categories;

    public CategoryAdapter(List<Categories> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.categories_name.setText(category.name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        protected TextView categories_name;

        public CardViewHolder(View convertView) {
            super(convertView);
            categories_name = (TextView) convertView.findViewById(R.id.name_text);
        }
    }
}
