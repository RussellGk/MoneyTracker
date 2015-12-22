package com.hardteam.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Expenses;

import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.CardViewHolder> {

    List<Expenses> expenses;

    public ExpensesAdapter(List<Expenses> expenses) {
        this.expenses = expenses;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);
        holder.name_text.setText(expense.name);
        holder.data_text.setText(expense.date);
        holder.sum_text.setText(expense.price);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

  public class CardViewHolder extends RecyclerView.ViewHolder{

    protected TextView name_text;
    protected TextView data_text;
    protected TextView sum_text;

    public CardViewHolder(View convertView) {
        super(convertView);
        name_text = (TextView) convertView.findViewById(R.id.name_text);
        data_text = (TextView) convertView.findViewById(R.id.date_text);
        sum_text =  (TextView) convertView.findViewById(R.id.sum_text);
    }
  }
}