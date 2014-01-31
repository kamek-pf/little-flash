package com.littleflash.pojo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class AlertMaker
{

    private Activity a;

    public AlertMaker(Activity a)
    {
        this.a = a;
    }
    
    public void firstRun()
    {
        Builder alert = new AlertDialog.Builder(a);
        alert.setTitle("Hi !");
        alert.setMessage( "This is your first time running \nthe app ! " +
                "Please confirm your email address.");
        alert.setPositiveButton("Got it", null);

        alert.show();
    }
    
    public void photoFailed()
    {
    	 Builder alert = new AlertDialog.Builder(a);
         alert.setTitle("Can't take picture");
         alert.setMessage("Cannot create file to save picture. Maybe you don't have enough space left ?");
         alert.setPositiveButton("OK", null);
            
         alert.show();
    }
    
    public void emailFailed()
    {
    	 Builder alert = new AlertDialog.Builder(a);
         alert.setTitle("wut");
         alert.setMessage("This is not a valid e-mail address D:");
         alert.setPositiveButton("My bad", null);
            
         alert.show();
    }
    
    public void invalidData()
    {
    	Builder alert = new AlertDialog.Builder(a);
        alert.setTitle("u wat mate ?");
        alert.setMessage("Bro I have no idea what you just flashed.");
        alert.setPositiveButton("duh", null); 

        alert.show();
    }
    
    public void itemSaved()
    {
    	Builder alert = new AlertDialog.Builder(a);
        alert.setTitle("Item saved");
        alert.setMessage("This item has been saved, you'll get notifications whenever it's on sale.");
        alert.setPositiveButton("OK", finisher); 

        alert.show();
    }

    public void emailSaved()
    {
    	Builder alert = new AlertDialog.Builder(a);
        alert.setTitle("Success !");
        alert.setMessage("Your new e-mail address has been saved.");
        alert.setPositiveButton("OK", finisher); 

        alert.show();
    }
    
    // OK button listener, takes you back to the main screen
    private DialogInterface.OnClickListener finisher =  new DialogInterface.OnClickListener() 
    {
        public void onClick(DialogInterface dialog, int which) {
            a.finish(); 
        }
    };
    
    public void needCodeReader()
    {
    	Builder alert = new AlertDialog.Builder(a);
        alert.setTitle("Missing dependency");
        alert.setMessage("Little Flash needs an Open Source application called Barcode Scanner"
        		+ " to run properly, please install it before you try again.");
        alert.setPositiveButton("Go to the Play Store", goToStore); 

        alert.show();
    }
    
 // OK button listener, takes you back to the main screen
    private DialogInterface.OnClickListener goToStore =  new DialogInterface.OnClickListener() 
    {
        public void onClick(DialogInterface dialog, int which) {
        	 Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
             Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
             a.startActivity(marketIntent);
        }
    };
}
