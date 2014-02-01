package com.littleflash.event;



import com.littleflash.pojo.Item;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class CheckboxListener implements OnClickListener {

    private Item item;

    public CheckboxListener(Item item){
        this.item = item;
    }

    @Override
    public void onClick(View v) {
        
        CheckBox cb = (CheckBox) v;
        
        if(v.getId() == cb.getId())
            item.setChecked(cb.isChecked());

    }

}
