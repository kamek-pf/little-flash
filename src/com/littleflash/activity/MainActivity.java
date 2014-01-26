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
import android.widget.EditText;

import com.littleflash.pojo.DataStoreHelper;
import com.littleflash.pojo.QRData;
import com.littleflash.activities.R;
import com.littleflash.activity.EmailSelector;
public class MainActivity extends Activity {

private SharedPreferences prefs = null;
private QRData data;
private EditText product_id;
private Button send;
private Context c;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    c = this.getApplicationContext();

    prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
    data = new QRData();

    product_id = (EditText) findViewById(R.id.p_id);
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
        data.setItemId(product_id.getText().toString());

        new SendThread(c).execute();
    }
};


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