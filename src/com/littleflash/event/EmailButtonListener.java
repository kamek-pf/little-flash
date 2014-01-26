package com.littleflash.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.littleflash.activities.R;
import com.littleflash.pojo.EmailHandler;

public class EmailButtonListener implements OnClickListener {

    private Activity a;
    private String email;
    private EditText emailField;
    private SharedPreferences prefs;

    public EmailButtonListener(Activity a, SharedPreferences prefs, EditText emailField)
    {
        this.a = a;
        this.prefs = prefs;
        this.emailField = emailField;
    }


    @Override
    public void onClick(View v) 
    {
        if(v.getId() == R.id.email_default)
        {
            email = EmailHandler.getDeviceEmail(a.getApplicationContext());
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailField.setText(email);
            else
                emailField.setText("");
        }        

        else if(v.getId() == R.id.email_save)
        {
            Builder alert = new AlertDialog.Builder(a);
            email = emailField.getText().toString();
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                prefs.edit().putString("UserEmail", email).commit();

                alert.setTitle("Success !");
                alert.setMessage("Your new e-mail address has been saved.");
                alert.setPositiveButton("OK", finisher); 

                alert.show();
            }
            else
            {
                emailField.setText("");
                alert.setTitle("wut");
                alert.setMessage("This is not a valid e-mail address D:");
                alert.setPositiveButton("My bad", null);
                alert.show();
            }
        }

    }

    // OK button listener, takes you back to the main screen
    private DialogInterface.OnClickListener finisher =  new DialogInterface.OnClickListener() 
    {
        public void onClick(DialogInterface dialog, int which) {
            a.finish(); 
        }
    };
}

