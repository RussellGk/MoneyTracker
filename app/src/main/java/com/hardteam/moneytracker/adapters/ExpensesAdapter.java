package com.hardteam.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ExpensesAdapter(List<Expenses> expenses, CardViewHolder.ClickListener clickListener) {

        this.clickListener = clickListener;

        this.expenses = expenses;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(convertView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);
        holder.name_text.setText(expense.name);
        holder.data_text.setText(expense.date);
        holder.sum_text.setText(expense.price);

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

    protected View selectedOverlay;

    private ClickListener clickListener;

    public CardViewHolder(View convertView, ClickListener clickListener) {
        super(convertView);
        name_text = (TextView) convertView.findViewById(R.id.name_text);
        data_text = (TextView) convertView.findViewById(R.id.date_text);
        sum_text =  (TextView) convertView.findViewById(R.id.sum_text);

        selectedOverlay = itemView.findViewById(R.id.selected_overlay);

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