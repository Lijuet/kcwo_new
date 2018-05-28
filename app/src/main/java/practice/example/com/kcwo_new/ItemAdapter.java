package practice.example.com.kcwo_new;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ItemAdapter extends ArrayAdapter<ItemClass> {
    Context context;
    int resId;
    private ArrayList<ItemClass> datas;

    public ItemAdapter(Context context, int resId, ArrayList<ItemClass> datas) {
        super(context, resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }

    @Override
    public int getCount(){
        return datas.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);
            ItemInfo holder = new ItemInfo(convertView);
            convertView.setTag(holder);
        }

        ItemInfo holder = (ItemInfo)convertView.getTag();

        TextView itemNameView = holder.itemNameView;
        TextView itemValueView = holder.itemValueView;

        ItemClass item = datas.get(position);

        itemNameView.setText(item.getName());
        itemValueView.setText(Integer.toString(item.getValue()));

        if(item.getType().equals("수입")) {
            //사용자가 지정한 이미지 설정
            itemValueView.setTextColor(context.getResources().getColor(R.color.colorIncome));
        }else if(item.getType().equals("지출")) {
            itemValueView.setTextColor(context.getResources().getColor(R.color.colorExpense));
        }else{
            itemValueView.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }

        return convertView;
    }

    @Override
    public ItemClass getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void initForTest(){//처음에 파일 읽어오기
        //1개의 데이터 추가
        Calendar date = Calendar.getInstance();
        date.set(2017,10,5);
        datas.add(new ItemClass("빼빼로",1300,"지출",date));
        Calendar date2 = Calendar.getInstance();
        date2.set(2017,11,15);
        datas.add(new ItemClass("용돈",50000,"수입",date2));
        Calendar date3 = Calendar.getInstance();
        date3.set(2017,9,01);
        datas.add(new ItemClass("음료수",2000,"지출",date3));
    }

    public void dataChanged(){
        sortList();
        this.notifyDataSetChanged();
    }

    public void addItem(ItemClass item){ datas.add(item); }

    public void removeItem(int position){
        datas.remove(position);
    }

    public void updateFirebase(){
        //data에 넣는 작업
        dataChanged();
    }

    public void sortList() {
        Collections.sort(datas, (ItemClass item1, ItemClass item2) -> item1.getDate().compareTo(item2.getDate()));
    }

    public int calTotalIncome(){
        int incomeSum = 0;
        ItemClass temp;
        for(int i = 0; i < datas.size(); i++){
            temp = datas.get(i);
            if(temp.getType() == "수입") incomeSum += temp.getValue();
        }

        return incomeSum;
    }

    public int calTotalExpense(){
        int expenseSum = 0;
        ItemClass temp;
        for(int i = 0; i < datas.size(); i++){
            temp = datas.get(i);
            if(temp.getType() == "지출") expenseSum += temp.getValue();
        }
        return expenseSum;

    }

    public int calCurrentMoney(){
        return calTotalIncome() - calTotalExpense();
    }
}