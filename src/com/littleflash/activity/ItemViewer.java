package com.littleflash.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.littleflash.activities.R;
import com.littleflash.pojo.QRData;

public class ItemViewer extends Activity {

	public static final String EXTRA_KEY_IN = "EXTRA_IN";
	 
    private TextView ref;
    private TextView price;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        QRData data = (QRData) getIntent().getExtras().getParcelable("data");

        ref = (TextView) findViewById(R.id.item_ref);
        price = (TextView) findViewById(R.id.item_price);
        info = (TextView) findViewById(R.id.item_info);

        ref.setText(data.getItemId() + " - " + data.getItemName());
        price.setText("" + data.getPrice() + " €");
        info.setText(data.getItemInfo());
    }

    // Set drop down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_options, menu);

        return true;
    }

    // Set drop down menu item's actions
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        if(item.getItemId() == R.id.email_settings)
        {
            Intent intent = new Intent(this, EmailSelector.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
