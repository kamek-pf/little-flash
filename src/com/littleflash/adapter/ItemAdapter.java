package com.littleflash.adapter;

import java.util.ArrayList;

import com.littleflash.activities.R;
import com.littleflash.event.CheckboxListener;
import com.littleflash.event.ItemNameListener;
import com.littleflash.pojo.Item;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item>{

    // Get application context to be able to inflate properly
    private Activity a;
    private ArrayList<Item> items;

    public ItemAdapter(Activity a, int resource, ArrayList<Item> items)
    {
        super(a.getApplicationContext(), resource, items);
        this.a = a;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get current task
        Item current = items.get(position);

        // Inflate if convertView is null
        if (convertView == null)
        {
            LayoutInflater vi = LayoutInflater.from(a.getApplicationContext());
            convertView = vi.inflate(R.layout.item, null);
        }
        
        // Find current list item's elements
        CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.l_checkbox);
        TextView ref = (TextView) convertView.findViewById(R.id.l_ref);
        TextView price = (TextView) convertView.findViewById(R.id.l_price);
        TextView info = (TextView) convertView.findViewById(R.id.l_info);

        // Set elements' states
        ref.setText(current.getRef());
        price.setText("" + current.getPrice() + " €");
        info.setText(current.getInfo());
        
        // Get item info
        String[] part = current.getRef().split("-");
        String idData = part[0];
        String nameData = part[1];
        double priceData = current.getPrice();
        String infoData = info.getText().toString();
        
        // Add action listener on checkbox
        ref.setOnClickListener(new ItemNameListener(a, idData, nameData, priceData, infoData));
        checkbox.setOnClickListener(new CheckboxListener(current));

        return convertView;
    }
}
