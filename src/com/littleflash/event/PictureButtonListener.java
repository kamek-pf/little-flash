package com.littleflash.event;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

import com.littleflash.activities.R;
import com.littleflash.activity.ItemViewer;

public class PictureButtonListener implements OnClickListener {

    private Activity a;

    public PictureButtonListener(Activity a)
    {
        this.a = a;
    }


    @Override
    public void onClick(View v) 
    {
        if(v.getId() == R.id.flash_add_pic)
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(a.getPackageManager()) != null) 
                a.startActivityForResult(takePictureIntent, ItemViewer.REQUEST_IMAGE_CAPTURE);
        }
    }
}

