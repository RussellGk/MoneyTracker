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

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */
public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.CardViewHolder> {

    List<Expenses> expenses;
    private CardViewHolder.ClickListener clickListener;
    private  Context context;
    private int lastPosition = -1;


    public ExpensesAdapter(Context context, List<Expenses> expenses, CardViewHolder.ClickListener clickListener) {

        this.clickListener = clickListener;
        this.expenses = expenses;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(convertView, clickListener);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if(position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);
        holder.name_text.setText(expense.name);
        holder.data_text.setText(expense.date);
        holder.sum_text.setText(expense.price);

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
        if (expenses.get(position) != null) {
            expenses.get(position).delete();
            expenses.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

  public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    protected TextView name_text;
    protected TextView data_text;
    protected TextView sum_text;

    protected CardView cardView;

    protected View selectedOverlay;

    private ClickListener clickListener;



    public CardViewHolder(final View itemView, ClickListener clickListener) {//convertView
        super(itemView);//convertView
        name_text = (TextView) itemView.findViewById(R.id.name_text);//convertView
        data_text = (TextView) itemView.findViewById(R.id.date_text);//convertView
        sum_text =  (TextView) itemView.findViewById(R.id.sum_text);//convertView

        selectedOverlay = itemView.findViewById(R.id.selected_overlay);

        cardView = (CardView) itemView.findViewById(R.id.card_view);

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