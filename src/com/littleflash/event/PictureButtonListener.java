package com.littleflash.event;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

import com.littleflash.activities.R;

public class PictureButtonListener implements OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
	
    private Activity a;
    private File photoFile;

    public PictureButtonListener(Activity a, File photoFile)
    {
        this.a = a;
        this.photoFile = photoFile;;
    }


    @Override
    public void onClick(View v) 
    {
        if(v.getId() == R.id.flash_add_pic)
        {
        	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        	// Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(a.getPackageManager()) != null) {
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    a.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }

        }
    }
}

