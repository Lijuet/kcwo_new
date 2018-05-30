package practice.example.com.kcwo_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    TextView current, totalIncome, totalExpense, budget, calculatedBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent intent = getIntent();

        Statistics info = (Statistics)intent.getSerializableExtra("STATISTICS");

        current = findViewById(R.id.value_current_statistics);
        totalIncome = findViewById(R.id.value_income_statistics);
        totalExpense = findViewById(R.id.value_expense_statistics);
        budget = findViewById(R.id.value_budget_statistics);
        calculatedBudget = findViewById(R.id.value_calculatedBudget_statistics);

        current.setText(String.valueOf(info.getCurrentMoney()));
        totalExpense.setText(String.valueOf(info.getTotalExpense()));
        totalIncome.setText(String.valueOf(info.getTotalIncome()));
        budget.setText(String.valueOf(info.getBudget()));
        calculatedBudget.setText(String.valueOf(info.getCalculatedBudget()));
    }
}
