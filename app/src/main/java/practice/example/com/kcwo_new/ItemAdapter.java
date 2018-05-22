package practice.example.com.kcwo_new;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
        datas.add(new ItemClass("빼빼로",1300,"지출"));
        datas.add(new ItemClass("용돈",50000,"수입"));
    }

    public void dataChanged(){ this.notifyDataSetChanged(); }

    public void addItem(ItemClass item){ datas.add(item); }

    public void removeItem(int position){
        datas.remove(position);
    }
}