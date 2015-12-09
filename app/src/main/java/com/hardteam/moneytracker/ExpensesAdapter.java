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
 * Created by RG on 06.12.2015.
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.CardViewHolder> {

    List<Expense> expenses;
//    static int foneColor;

    public ExpensesAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.name_text.setText(expense.getTitle());
        holder.data_text.setText(expense.getHumanDate(expense.getDate()));
        holder.sum_text.setText(expense.getMoney(expense.getSum()));
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
//    public ExpensesAdapter(Context context, List<Expense> expenses) {
//        super(context,0,expenses);
//        this.expenses = expenses;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//
//        Expense expense = getItem(position);
//        if(convertView == null)
//        {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
//        }
//
//        RelativeLayout fone = (RelativeLayout) convertView.findViewById(R.id.relative_item);
//        foneColor = rgb(getIntColor(),getIntColor(),getIntColor());
//        fone.setBackgroundColor(foneColor);
//
//        TextView name =(TextView) convertView.findViewById(R.id.name_text);
//        TextView data =(TextView) convertView.findViewById(R.id.date_text);
//        TextView sum = (TextView) convertView.findViewById(R.id.sum_text);
//
//        name.setText(expense.getTitle());
//        data.setText(expense.getHumanDate(expense.getDate()));
//        sum.setText(expense.getMoney(expense.getSum()));
//        return convertView;
//    }
//    private static int getIntColor()
//   {
//    int rnd = 0 + (int)(Math.random() * ((255 - 0) + 1));
//
//    return rnd;
//   }

//}
