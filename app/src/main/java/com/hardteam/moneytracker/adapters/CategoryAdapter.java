package com.hardteam.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private Context context;
    private int lastPosition = -1;

    public CategoryAdapter(Context context, List<Categories> categories, CardViewHolder.ClickListener clickListener) {

        this.clickListener = clickListener;
        this.categories = categories;
        this.context = context;
    }

    @Override
    public CategoryAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CardViewHolder(convertView, clickListener);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if(position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);// Animation!
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.categories_name.setText(category.name);

        setAnimation(holder.cardView, position);

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

        protected CardView cardView;

        protected View selectedOverlay;

        private ClickListener clickListener;

        public CardViewHolder(final View itemView, ClickListener clickListener)
        {
            super(itemView);
            categories_name = (TextView) itemView.findViewById(R.id.name_text);

            selectedOverlay = itemView.findViewById(R.id.selected_overlay_category);
            cardView = (CardView) itemView.findViewById(R.id.category_list_one);

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
