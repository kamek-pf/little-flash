package com.littleflash.event;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

import com.littleflash.activities.R;
import com.littleflash.activity.ItemViewer;
import com.littleflash.pojo.AlertMaker;
import com.littleflash.pojo.QRData;

public class ItemNameListener implements OnClickListener {

	private Activity a;
    private String itemId;
    private String itemName;
    private double price;
    private String itemInfo;
    
    public ItemNameListener(Activity a, String itemId, String itemName, double price, String itemInfo)
    {
    	this.a = a;
    	this.itemId = itemId;
    	this.itemName = itemName;
    	this.price = price;
    	this.itemInfo = itemInfo;
    }


    @Override
    public void onClick(View v) 
    {
        if(v.getId() == R.id.l_ref)
        {
        	QRData data = new QRData();
        	data.setItemId(itemId);
        	data.setItemName(itemName);
        	data.setPrice(price);
        	data.setItemInfo(itemInfo);
        	
        	data.setSimpleView(true);
        	
            Intent i = new Intent(a, ItemViewer.class);
            i.putExtra("data", data);
            a.startActivity(i);
        }
    }
}

