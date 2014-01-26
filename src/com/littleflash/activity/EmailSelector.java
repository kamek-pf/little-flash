package com.littleflash.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.littleflash.activities.R;
import com.littleflash.event.EmailButtonListener;
import com.littleflash.pojo.EmailHandler;

public class EmailSelector extends Activity {

	 private SharedPreferences prefs = null;
     private String email;
     private EditText emailField;
     private Button defaultButton, saveButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);

        prefs = getSharedPreferences("com.littleflash.core", MODE_PRIVATE);
        emailField = (EditText) this.findViewById(R.id.email_field);
        
        email = prefs.getString("UserEmail", "youremail@yourprovider.xyz");
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
            
            // Build an alert
            Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hi !");
            alert.setMessage( "This is your first time running the app!\n" +
                    "Please confirm your email address.");
            alert.setPositiveButton("Got it", null);

            // Fetch main email address and save it, or put a place holder
            email = EmailHandler.getDeviceEmail(this.getApplicationContext());
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                prefs.edit().putString("UserEmail", email).commit();
                emailField.setText(email);
            }

            alert.show();
        }
    }

}
