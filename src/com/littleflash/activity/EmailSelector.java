package com.littleflash.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.littleflash.activities.R;
import com.littleflash.event.EmailButtonListener;
import com.littleflash.pojo.AlertMaker;
import com.littleflash.pojo.EmailHandler;

public class EmailSelector extends Activity {

	 private SharedPreferences prefs = null;
     private String email;
     private EditText emailField;
     private Button defaultButton, saveButton;
     private AlertMaker alert;
     private Context c;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        c = this.getApplicationContext();

        alert = new AlertMaker(this);
        prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
        emailField = (EditText) this.findViewById(R.id.email_field);
        
        email = EmailHandler.getEmail(c); 
        emailField.setText(email);

        defaultButton = (Button) findViewById(R.id.email_default);
        saveButton = (Button) findViewById(R.id.email_save);

        defaultButton.setOnClickListener(new EmailButtonListener(this, prefs, emailField));
        saveButton.setOnClickListener(new EmailButtonListener(this, prefs, emailField));
    }

    
    // On first run
    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) 
        {
            prefs.edit().putBoolean("firstrun", false).commit();
            
            // Fetch main email address and save it, or put a place holder
            email = EmailHandler.getDeviceEmail(c);
            emailField.setText(email);
            
            // Display an alert
            alert.firstRun();
        }
    }

}
