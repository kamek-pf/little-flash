package com.littleflash.event;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.littleflash.activities.R;
import com.littleflash.pojo.AlertMaker;
import com.littleflash.pojo.EmailHandler;

public class EmailButtonListener implements OnClickListener {

    private Activity a;
    private String email;
    private EditText emailField;
    private SharedPreferences prefs;
    private AlertMaker alert;

    public EmailButtonListener(Activity a, SharedPreferences prefs, EditText emailField)
    {
        this.a = a;
        this.prefs = prefs;
        this.emailField = emailField;
        this.alert = new AlertMaker(a);
    }


    @Override
    public void onClick(View v) 
    {
        if(v.getId() == R.id.email_default)
        {
            email = EmailHandler.getDeviceEmail(a.getApplicationContext());
            emailField.setText(email);
        }
        else if(v.getId() == R.id.email_save)
        {
            email = emailField.getText().toString();
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                prefs.edit().putString("UserEmail", email).commit();
                alert.emailSaved();
            }
            else
            {
                emailField.setText("");
                alert.emailFailed();
            }
        }

    }
}

