package com.littleflash.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.littleflash.pojos.EmailHandler;

public class EmailSelector extends Activity {

	 private SharedPreferences prefs = null;
     private String email;
     private EditText emailField;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);

        prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
        emailField = (EditText) this.findViewById(R.id.email_field);
        
        email = prefs.getString("UserEmail", "youremail@yourprovider.xyz");
        emailField.setText(email);
    }

    
    // On first run
    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) 
        {
            prefs.edit().putBoolean("firstrun", false).commit();
            
            // Build an alert
            Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hi !");
            alert.setMessage( "This is your first time running the app!\n" +
                    "Please confirm your email address.");
            alert.setPositiveButton("Got it", null);

            // Fetch main email address and save it, or put a place holder
            email = EmailHandler.getEmail(this.getApplicationContext());
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                prefs.edit().putString("UserEmail", email).commit();
                emailField.setText(email);
            }

            alert.show();
        }
    }

}
