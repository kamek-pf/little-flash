package com.littleflash.activities;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.client.util.DateTime;
import com.littleflash.pojos.DataStoreHelper;
import com.littleflash.pojos.QRData;
import com.littleflash.activities.EmailSelector;


public class MainActivity extends Activity {

	private SharedPreferences prefs = null;
    private QRData data;
    private EditText shop_id, product_type, product_id, price;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
        data = new QRData();

        shop_id = (EditText) findViewById(R.id.s_id);
        product_type = (EditText) findViewById(R.id.p_type);
        product_id = (EditText) findViewById(R.id.p_id);
        price = (EditText) findViewById(R.id.p_price);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(sendListener);
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

    // Create an anonymous implementation of OnClickListener
    private OnClickListener sendListener = new OnClickListener() {
        public void onClick(View v) {
            data.setShopId(shop_id.getText().toString());
            data.setItemType(product_type.getText().toString());
            data.setItemId(product_id.getText().toString());
            data.setPrice(price.getText().toString());
            data.setFlashDate(new DateTime(new Date()));

            new SendThread().execute();
        }
    };


    // Thread for netwok stuff
    private class SendThread extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... param) 
        {  
            DataStoreHelper dataStore = new DataStoreHelper(data);
            dataStore.sendData();
            return null;
        }
    }
   
    // Handle first run
    @Override
    protected void onResume() 
    {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) 
        {
            Intent intent = new Intent(this, EmailSelector.class);
            this.startActivity(intent);
        }
    }

}
