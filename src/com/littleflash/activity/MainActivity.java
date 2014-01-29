package com.littleflash.activity;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;

import com.littleflash.pojo.AlertMaker;
import com.littleflash.pojo.DataStoreHelper;
import com.littleflash.pojo.DependencyChecker;
import com.littleflash.pojo.QRData;
import com.littleflash.activities.R;
import com.littleflash.activity.EmailSelector;
public class MainActivity extends Activity {

    private SharedPreferences prefs = null;
    private QRData data;
    private TextView flashData;
    private Button send;
    private DependencyChecker checker;
    private Context c;
    private AlertMaker alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        c = this.getApplicationContext();
        alert = new AlertMaker(this);
        checker = new DependencyChecker(c);

        prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
        data = new QRData();

        flashData = (TextView) findViewById(R.id.flash_data);
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
            if(checker.readerInstalled())
            {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); //for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
                intent.putExtra("SAVE_HISTORY", false);//this stops saving ur barcode in barcode scanner app's history
                startActivityForResult(intent, 0); 
            }
            else
            	alert.needCodeReader();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentData) 
    {
        String result;
        super.onActivityResult(requestCode, resultCode, intentData);
        if (requestCode == 0) 
        {
            if (resultCode == RESULT_OK) 
            {
                result = intentData.getStringExtra("SCAN_RESULT");
                flashData.setText(result); //this is the result
                data.process(result); 
                
                new SendThread(c).execute();
            } 
            else if (resultCode == RESULT_CANCELED) 
            {
                // Handle cancel
            }
        }
    }

    // Thread for netwok stuff
    private class SendThread extends AsyncTask<Void, Void, Void>
    {
        private Context c;

        public SendThread(Context c)
        {
            this.c = c;
        }
        @Override
        protected Void doInBackground(Void... param)
        {
            DataStoreHelper dataStore = new DataStoreHelper(data, c);
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
