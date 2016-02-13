package com.hardteam.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardteam.moneytracker.Category;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by RG on 07.12.2015.
 */

public class CategoryAdapter extends SelectableAdapter<CategoryAdapter.CardViewHolder>
{
    List<Categories> categories;

    private CardViewHolder.ClickListener clickListener;

    public CategoryAdapter(List<Categories> categories, CardViewHolder.ClickListener clickListener) {

        this.clickListener = clickListener;

        this.categories = categories;
    }

    @Override
    public CategoryAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CardViewHolder(convertView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.categories_name.setText(category.name);

        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    public void removeItems(List<Integer> positions) {

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            removeItem(positions.get(0));
            positions.remove(0);
        }

    }

    public void removeItem(int position) {
        if (categories.get(position) != null) {
            categories.get(position).delete();
            categories.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        protected TextView categories_name;

        protected View selectedOverlay;

        private ClickListener clickListener;

        public CardViewHolder(View convertView, ClickListener clickListener)
        {
            super(convertView);
            categories_name = (TextView) convertView.findViewById(R.id.name_text);

            selectedOverlay = itemView.findViewById(R.id.selected_overlay_category);

            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null)
            {
                clickListener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return clickListener != null && clickListener.onItemLongClicked(getAdapterPosition());
        }

        public interface ClickListener
        {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }

}
