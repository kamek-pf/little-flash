package com.littleflash.pojo;

import android.content.Context;
import android.content.SharedPreferences;

// Manage Item's persistence
public class ItemHandler 
{

    // Get current Item count
    public static int getItemCount(Context c) 
    {
        SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
        return prefs.getInt("itemCount", 0);
    }

    // Decrease Item count
    public static void decreaseItemCount(Context c)
    {
        int count = 0;
        SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
        count =  prefs.getInt("itemCount", 0);
        if(count != 0)
            prefs.edit().putInt("itemCount", count - 1).commit();
    }

    // Increase Item count
    public static void increaseItemCount(Context c)
    {
        int count = 0;
        SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
        count =  prefs.getInt("itemCount", 0);
        prefs.edit().putInt("itemCount", count + 1).commit();
    }

    // Store Item, start at indice 1
    public static void storeItem(Context c, Item i)
    {
        int indice = 0;
        SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
        indice = getItemCount(c);
        indice++;

        prefs.edit().putString("item_" + indice + "_uuid", i.getUuid()).commit();
        prefs.edit().putString("item_" + indice + "_ref", i.getRef()).commit();
        prefs.edit().putFloat("item_" + indice + "_price", (float) i.getPrice()).commit();
        prefs.edit().putString("item_" + indice + "_info", i.getInfo()).commit();

        increaseItemCount(c);
    }

    // Delete item i
    public static void deleteItem(Context c, int i)
    {
        // decrease count
        SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
        decreaseItemCount(c);
        
        // delete item i
        prefs.edit().remove("item_" + i + "_uuid").commit();
        prefs.edit().remove("item_" + i + "_ref").commit();
        prefs.edit().remove("item_" + i + "_price").commit();
        prefs.edit().remove("item_" + i + "_info").commit();

        // decrease indices
        for(int j = i + 1; j <= getItemCount(c) + 1; j++)
        {
            int back = j - 1;
            prefs.edit().putString("item_" + back + "_uuid", prefs.getString("item_" + j  + "_uuid", "")).commit();
            prefs.edit().putString("item_" + back + "_ref", prefs.getString("item_" + j  + "_ref", "")).commit();
            prefs.edit().putFloat("item_" + back + "_price", prefs.getFloat("item_" + j  + "_price", (float) 0.0)).commit();
            prefs.edit().putString("item_" + back + "_info", prefs.getString("item_" + j  + "_info", "")).commit();
        }
    }

}
