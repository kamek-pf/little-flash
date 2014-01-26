package com.littleflash.pojo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;

// Find the device's main email address
public class EmailHandler 
{

	// Get the device's main email address
    public static String getDeviceEmail(Context c) 
    {
        AccountManager accountManager = AccountManager.get(c); 
        Account account = getAccount(accountManager);

        if (account == null) 
            return null;
         else 
            return account.name;
    }
    
    // Get the email address saved in Little Flash' preferences
    public static String getEmail(Context c) 
    {
        String email = "";
        SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
        email = prefs.getString("UserEmail", "youremail@yourprovider.xyz");
        
        return email;
    }

    private static Account getAccount(AccountManager accountManager) 
    {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        
        if (accounts.length > 0)
            account = accounts[0];      
        else
            account = null;
        
        return account;
    }
}
