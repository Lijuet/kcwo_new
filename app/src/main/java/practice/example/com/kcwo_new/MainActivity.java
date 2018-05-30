package practice.example.com.kcwo_new;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    int add_mode_set = 1, edit_mode_set = 2;
    private ItemAdapter adapter;
    Button btn_add,  btn_statistic;
    TextView budget, currentMoney;
    TextView income, expense;
    ListView listView;
    Statistics statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        statistics = new Statistics();

        adapter = new ItemAdapter(this, R.layout.custom_view_item,  new ArrayList<ItemClass>());
        adapter.initForTest();//임시 초기값 확인

        //id가져오기
        listView = findViewById(R.id.view_list);
        btn_add = findViewById(R.id.button_add);
        btn_statistic = findViewById(R.id.button_statistic);
        budget = findViewById(R.id.value_budget);
        currentMoney = findViewById(R.id.value_current);
        income = findViewById(R.id.value_income);
        expense = findViewById(R.id.value_expense);

        //클릭리스너 객체 생성
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId()){
                    case R.id.button_add:
                        Intent intent_MainToAdd = new Intent(MainActivity.this,ItemInfoActivity.class);
                        startActivityForResult(intent_MainToAdd,0);
                        break;
                    case R.id.button_statistic:
                        Intent intent_MainToStatistics = new Intent(MainActivity.this, StatisticsActivity.class);
                        intent_MainToStatistics.putExtra("STATISTICS",statistics);
                        startActivity(intent_MainToStatistics);
                        break;
                    case R.id.value_budget:
                        AlertDialog.Builder budgetDialog = new AlertDialog.Builder(MainActivity.this);

                        budgetDialog.setTitle("예산 변경하기");       // 제목 설정
                        budgetDialog.setMessage("예산을 다시 설정하시오");   // 내용 설정

                        final EditText et = new EditText(MainActivity.this);
                        et.setText(String.valueOf(statistics.getBudget()));
                        budgetDialog.setView(et);

                        budgetDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int budget = Integer.parseInt(et.getText().toString());
                                statistics.setBudget(budget);
                                updateMiniStatistics();
                                dialog.dismiss();
                            }
                        });

                        budgetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        budgetDialog.show();
                }
            }
        };
        //어댑터 설정
        listView.setAdapter(adapter);


        //아이템 수정화면으로
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent_MainToEdit = new Intent(MainActivity.this, ItemInfoActivity.class);
                intent_MainToEdit.putExtra("ITEM", adapter.getItem(position));
                intent_MainToEdit.putExtra("POSITION",position);
                intent_MainToEdit.putExtra("EDITMODE",true);
                startActivityForResult(intent_MainToEdit,0);
            }
        });

        //슬라이드 삭제
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions)
                                    adapter.removeItem(position);
                                adapter.dataChanged();

                                Toast.makeText(getApplicationContext(),"dismiss2",Toast.LENGTH_SHORT).show();
                                updateMiniStatistics();
                            }
                        });

        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());
        btn_add.setOnClickListener(listener);
        btn_statistic.setOnClickListener(listener);
        budget.setOnClickListener(listener);

        updateMiniStatistics();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (resultCode == add_mode_set) {
            adapter.addItem((ItemClass) intent.getSerializableExtra("ADDINFO"));
        } else if (resultCode == edit_mode_set) {
            ItemClass editItem = (ItemClass) intent.getSerializableExtra("EDITINFO");
            int pos = intent.getIntExtra("POSITION", -1);
            if (pos != -1) {
                adapter.getItem(pos).setName(editItem.getName());
                adapter.getItem(pos).setValue(editItem.getValue());
                adapter.getItem(pos).setType(editItem.getType());
                adapter.getItem(pos).setDate(editItem.getDate());
                Toast.makeText(this, adapter.getItem(pos).getDate().DATE, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        adapter.dataChanged();
        updateMiniStatistics();
    }

    public void updateMiniStatistics(){
        statistics.setTotalIncome(adapter.calTotalIncome());
        statistics.setTotalExpense(adapter.calTotalExpense());
        statistics.setCurrentMoney();

        currentMoney.setText(String.valueOf(statistics.getCurrentMoney()));
        income.setText(String.valueOf(statistics.getTotalIncome()));
        expense.setText(String.valueOf(statistics.getTotalExpense()));

        budget.setText(String.valueOf(statistics.getCalculatedBudget()));
        if(statistics.getTotalExpense() >= statistics.getBudget() * 0.7) budget.setTextColor(getApplicationContext().getResources().getColor(R.color.colorWarning));
        else if(statistics.getTotalExpense() >= statistics.getBudget() * 0.4) budget.setTextColor(getApplicationContext().getResources().getColor(R.color.colorSoso));
        else budget.setTextColor(getApplicationContext().getResources().getColor(R.color.colorSafe));
    }
}
