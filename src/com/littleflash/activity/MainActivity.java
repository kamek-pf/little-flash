package com.littleflash.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.littleflash.activities.R;
import com.littleflash.adapter.ItemAdapter;
import com.littleflash.pojo.AlertMaker;
import com.littleflash.pojo.DataStoreHelper;
import com.littleflash.pojo.DependencyChecker;
import com.littleflash.pojo.ItemHandler;
import com.littleflash.pojo.QRData;
import com.littleflash.pojo.Item;
public class MainActivity extends Activity {

	private static ArrayList<Item> itemList = new ArrayList<Item>();
    private SharedPreferences prefs = null;
    private static ItemAdapter adapter;
    private QRData data;
    private Button send;
    private DependencyChecker checker;
    private Context c;
    private AlertMaker alert;
    private ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        c = this.getApplicationContext();
        data = new QRData();
        alert = new AlertMaker(this);
        checker = new DependencyChecker(c);
        
        prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
        
        send = (Button) findViewById(R.id.send);    
        send.setOnClickListener(sendListener);
    }

    // Set drop down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_options_main, menu);

        return true;
    }

    // Set drop down menu item's actions
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
    	boolean dataChanged = false;
        if(item.getItemId() == R.id.email_settings)
        {
            Intent intent = new Intent(this, EmailSelector.class);
            this.startActivity(intent);
        }
        else if(item.getItemId() == R.id.clear_selected)
        {
        	for(int i = itemList.size() - 1; i >= 0; i--)
        	{
        		Log.i("checkbox", "found item");
        		Item current = itemList.get(i);
                if(current.isChecked())
                {
                    itemList.remove(current);
                    ItemHandler.deleteItem(c, ItemHandler.getItemIndice(c, current));
                    dataChanged = true;
                    Log.i("checkbox", "found item to be deleted");
                } 
            }
        	 if(dataChanged)
                 adapter.notifyDataSetChanged();

        }

        return super.onOptionsItemSelected(item);
    }

    // Create an anonymous implementation of OnClickListener
    private OnClickListener sendListener = new OnClickListener() {
        public void onClick(View v) {
            if(checker.readerInstalled())
            {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); 
                intent.putExtra("SAVE_HISTORY", false); // This stops saving your code in ZXing's history
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
                data.process(result); 
                
                if(data.getItemId().equals(""))
                	alert.invalidData();
                else
                {
                    Intent i = new Intent(this, ItemViewer.class);
                    i.putExtra("data", data);
                	new SendThread(c).execute();
                    startActivity(i);
                }
            } 
            else if (resultCode == RESULT_CANCELED) 
                alert.invalidData();
        }
    }

    // Thread for network stuff
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

    @Override
    protected void onResume()
    {
        super.onResume();

        // Handle first run
        if (prefs.getBoolean("firstrun", true))
        {
            Intent intent = new Intent(this, EmailSelector.class);
            this.startActivity(intent);
        }
        
        // Set up the list
        lst = (ListView) findViewById(R.id.display_list); 
        adapter = new ItemAdapter(this, R.layout.item, itemList);
        lst.setAdapter(adapter);
        itemList = ItemHandler.getList(c);
        adapter.notifyDataSetChanged();
        
        ItemViewer.HAS_PIC = false;
    }

	public static ArrayList<Item> getItemList() {
		return itemList;
	}
    
    public static ItemAdapter getAdapter() {
        return adapter;
    }
}
