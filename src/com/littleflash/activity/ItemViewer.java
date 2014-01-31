package com.littleflash.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleflash.activities.R;
import com.littleflash.event.PictureButtonListener;
import com.littleflash.pojo.EmailHandler;
import com.littleflash.pojo.QRData;

public class ItemViewer extends Activity {

	private static final int REQUEST_TAKE_PHOTO = 1;
	public static boolean HAS_PIC = false;

    private TextView ref, price, info;
    private Button picBtn, saveBtn, delBtn;
    private ImageView photoView;
    private String photoPath;
    private File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        QRData data = (QRData) getIntent().getExtras().getParcelable("data");
        photoPath = Environment.getExternalStorageDirectory() + "/tmp.jpg";
        photoFile = new File(Environment.getExternalStorageDirectory() + "/tmp.jpg");


        // Get views
        ref = (TextView) findViewById(R.id.item_ref);
        price = (TextView) findViewById(R.id.item_price);
        info = (TextView) findViewById(R.id.item_info);
        picBtn = (Button) findViewById(R.id.flash_add_pic);
        saveBtn = (Button) findViewById(R.id.flash_save);
        delBtn = (Button) findViewById(R.id.flash_del);
        photoView = (ImageView) findViewById(R.id.img_thumb);

        // Set TextViews' text
        ref.setText(data.getItemId() + " - " + data.getItemName());
        price.setText("" + data.getPrice() + " €");
        info.setText(data.getItemInfo());

        // Set listeners
        picBtn.setOnClickListener(new PictureButtonListener(this, photoFile));
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
    
    @Override
    protected void onResume() {
        super.onResume();
        if(HAS_PIC)
        {
	        // Tweak ratio depending on orientation
            int orientation, targetW, targetH, scaleFactor, photoH, photoW;
	        DisplayMetrics displaymetrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	        
	        // Get the dimensions of the bitmap
	        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	        bmOptions.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(photoPath, bmOptions);
	        photoH = bmOptions.outHeight;
	        photoW = bmOptions.outWidth;
	        orientation = this.getResources().getConfiguration().orientation;
	        if(orientation == Configuration.ORIENTATION_PORTRAIT)
	        {
	        	targetW = displaymetrics.widthPixels - (displaymetrics.widthPixels * 30 / 100);
	        	scaleFactor = photoW/targetW;
	        }
	        else
	        {
	        	targetH = displaymetrics.heightPixels - (displaymetrics.heightPixels * 30 / 100);
	        	scaleFactor = photoH/targetH;
	        }

	        // Decode the image file into a Bitmap sized to fill the View
	        bmOptions.inJustDecodeBounds = false;
	        bmOptions.inSampleSize = scaleFactor;
	        bmOptions.inPurgeable = true;
	
	        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
	        photoView.setImageBitmap(bitmap);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) 
        	HAS_PIC = true;
     }
}
