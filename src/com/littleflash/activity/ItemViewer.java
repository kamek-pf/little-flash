package com.littleflash.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleflash.activities.R;
import com.littleflash.event.PictureButtonListener;
import com.littleflash.pojo.QRData;

public class ItemViewer extends Activity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private TextView ref, price, info;
    private Button picBtn, saveBtn, delBtn;
    private ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        QRData data = (QRData) getIntent().getExtras().getParcelable("data");

        // Get views
        ref = (TextView) findViewById(R.id.item_ref);
        price = (TextView) findViewById(R.id.item_price);
        info = (TextView) findViewById(R.id.item_info);
        picBtn = (Button) findViewById(R.id.flash_add_pic);
        saveBtn = (Button) findViewById(R.id.flash_save);
        delBtn = (Button) findViewById(R.id.flash_del);
        thumbnail = (ImageView) findViewById(R.id.img_thumb);

        // Set TextViews' text
        ref.setText(data.getItemId() + " - " + data.getItemName());
        price.setText("" + data.getPrice() + " €");
        info.setText(data.getItemInfo());

        // Set listeners
        picBtn.setOnClickListener(new PictureButtonListener(this));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) 
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            thumbnail.setImageBitmap(imageBitmap);
        }
    }
}
