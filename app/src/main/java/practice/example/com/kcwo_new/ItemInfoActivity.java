package practice.example.com.kcwo_new;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class ItemInfoActivity extends AppCompatActivity {
    int cancel_mode_set = 0, add_mode_set = 1, edit_mode_set = 2;
    private Button btnCancel, btnComplete;
    private RadioGroup typeRadioGroup;
    private RadioButton rbtnIncome, rbtnExpense;
    private EditText editDate, editName, editValue;

    String type;

    private Date date;

    //인덴트 불로오기
    Intent intent;
    Intent intent_ItemInfoToMain;
    boolean edit_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        //findView처리
        rbtnIncome = (RadioButton)findViewById(R.id.itemInfo_rbtnIncome);
        rbtnExpense = (RadioButton)findViewById(R.id.itemInfo_rbtnExpense);
        typeRadioGroup = (RadioGroup)findViewById(R.id.typeRadioGroup);
        btnCancel = (Button)findViewById(R.id.itemInfo_btnCancel);
        btnComplete = (Button)findViewById(R.id.itemInfo_btnComplete);
        editName = (EditText)findViewById(R.id.itemInfo_editName);
        editValue = (EditText)findViewById(R.id.itemInfo_editValue);

        //인덴트 설정
        intent_ItemInfoToMain = new Intent();
        intent = getIntent();
        edit_mode = intent.getBooleanExtra("EDITMODE",false);

        //edit 모드라면 값 로딩
        if(edit_mode) {
            ItemClass item = (ItemClass) intent.getSerializableExtra("ITEM");
            editName.setText(item.getName());
            editValue.setText(String.valueOf(item.getValue()));
            if(item.getType().equals("수입")){
                rbtnIncome.setChecked(true);
                rbtnExpense.setChecked(false);
            }else if(item.getType().equals("지출")){
                rbtnIncome.setChecked(false);
                rbtnExpense.setChecked(true);

            }

        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.itemInfo_btnComplete:
                        //값 받아오기
                        String name = editName.getText().toString();
                        int value = Integer.parseInt(editValue.getText().toString());

                        if(rbtnIncome.isChecked()) type = "수입";
                        else type = "지출";

                        //값 전달
                        if(!edit_mode) {//새로운 항목 만들어 인덴트에 추가하여 전달
                            intent_ItemInfoToMain.putExtra("ADDINFO", new ItemClass(name,value,type));
                            setResult(add_mode_set, intent_ItemInfoToMain);
                        }
                        else {//기존 인덴트 속 아이템 꺼내와 변경하기
                            intent_ItemInfoToMain.putExtra("POSITION",intent.getIntExtra("POSITION",-1));
                            intent_ItemInfoToMain.putExtra("EDITINFO", new ItemClass(name,value,type));
                            setResult(edit_mode_set, intent_ItemInfoToMain);
                        }
                        finish();
                        break;
                    case R.id.itemInfo_btnCancel:
                        finish();
                        break;
                }
            }
        };
        btnComplete.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }
}