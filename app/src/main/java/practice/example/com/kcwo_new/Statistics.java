package practice.example.com.kcwo_new;


import android.util.Log;

import java.io.Serializable;

public class Statistics implements Serializable{
    private int budget;
    private int totalIncome;
    private int totalExpense;
    private int currentMoney;

    Statistics(){
        budget = 0;
        totalExpense = 0;
        totalIncome = 0;
        currentMoney = 0;
    }

    public void setBudget(int budget){ this.budget = budget; }
    public void setTotalIncome(int totalIncome){ this.totalIncome = totalIncome; }
    public void setTotalExpense(int totalExpense){ this.totalExpense = totalExpense; }
    public void setCurrentMoney(){ this.currentMoney = totalIncome - totalExpense; }

    public int getBudget(){ return budget; }
    public int getCalculatedBudget(){ return budget - totalExpense; }
    public int getTotalIncome(){ return totalIncome; }
    public int getTotalExpense(){ return totalExpense; }
    public int getCurrentMoney(){ return currentMoney; }




}