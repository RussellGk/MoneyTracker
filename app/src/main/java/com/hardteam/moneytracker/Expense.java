package com.hardteam.moneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RG on 06.12.2015.
 */
public class Expense {
//     public String title;
//     public String sum;//Add int abd set and get methods

     private String title;
     private Date date;
     private int sum;

     public Expense(String title,Date date, int sum)
     {
          this.title = title;
          this.date = date;
          this.sum = sum;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public int getSum() {
          return sum;
     }

     public void setSum(int sum) {
          this.sum = sum;
     }

     public Date getDate() {
          return date;
     }

     public void setDate(Date date) {
          this.date = date;
     }

     public String getHumanDate(Date date)
     {
          SimpleDateFormat data_human = new SimpleDateFormat("dd-MM-yyyy");
          return data_human.format(date);
     }

     public String getMoney(int sum)
     {
          return Integer.toString(sum);
     }
}

