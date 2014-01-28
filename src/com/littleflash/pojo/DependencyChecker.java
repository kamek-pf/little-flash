package com.littleflash.pojo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class DependencyChecker
{

    private Context c;
    private PackageManager pm;

    public DependencyChecker(Context c)
    {
        this.c = c;
    }

    public boolean readerInstalled()
    {
        pm = c.getPackageManager();
        try 
        {
            pm.getPackageInfo("com.google.zxing.client.android", PackageManager.GET_ACTIVITIES);
            return true;
        } 
        catch (NameNotFoundException e) 
        {
            return false;
        }

    }
}
