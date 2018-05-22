package practice.example.com.kcwo_new;

import android.view.View;
import android.widget.TextView;

public class ItemInfo {
    public TextView itemNameView;
    public TextView itemValueView;

    public ItemInfo(View root){
        itemNameView = root.findViewById(R.id.list_item_name);
        itemValueView = root.findViewById(R.id.list_item_value);
    }
}