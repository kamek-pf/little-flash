package com.littleflash.event;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.littleflash.activities.R;
import com.littleflash.pojo.AlertMaker;
import com.littleflash.pojo.Item;
import com.littleflash.pojo.ItemHandler;

public class SaveButtonListener implements OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
	
    private Activity a;
    private String uuid, ref, info;
    private double price;

    public SaveButtonListener(Activity a, String uuid, TextView ref, TextView price, TextView info)
    {
        this.a = a;
        this.uuid = uuid;
        this.ref = ref.getText().toString();
        this.price = Double.parseDouble(price.getText().toString());
        this.info = info.getText().toString();
        this.a.getSharedPreferences("com.littleflash.core", Activity.MODE_PRIVATE);
    }


    @Override
    public void onClick(View v) 
    {
        if(v.getId() == R.id.flash_save)
        {
            // Create a new Item to store in the SQLite database
            Item storeItem = new Item(uuid, ref, price, info);
            ItemHandler.storeItem(a.getApplicationContext(), storeItem);
            AlertMaker alert = new AlertMaker(a);
            alert.itemSaved();
        }
    }
}

