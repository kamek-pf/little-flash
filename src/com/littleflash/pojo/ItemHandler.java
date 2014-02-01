package com.littleflash.pojo;


import java.util.ArrayList;

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
    
    public static Item getItem(Context c, int indice)
    {
    	SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
    	Item item = new Item(
	    			prefs.getString("item_" + indice  + "_uuid", ""),
	    			prefs.getString("item_" + indice  + "_ref", ""),
	    			(double) prefs.getFloat("item_" + indice  + "_price", (float) 0.0),
	    			prefs.getString("item_" + indice  + "_info", ""),
	    			prefs.getBoolean("item_" + indice  + "_haspic", false)
    			);
    
    	return item;
    }
    
    public static int getItemIndice(Context c, Item item)
    {
    	SharedPreferences prefs = c.getSharedPreferences("com.littleflash.core", Context.MODE_PRIVATE);
    	int indice = 0;
    	String[] part = prefs.getString("item_" + indice  + "_uuid", "").split("_");
    	indice = Integer.parseInt(part[2]);
    	
    	return indice;
    }
    
    public static ArrayList<Item> getList(Context c)
    {
    	ArrayList<Item> itemList = new ArrayList<Item>();
    	if(getItemCount(c) > 0)
    	{
    	    for(int i = 1; i <= getItemCount(c); i++)
                itemList.add(getItem(c, i));
    	}
    
    	return itemList;
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
        prefs.edit().putBoolean("item_" + indice + "_haspic", i.hasPic()).commit();

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
        prefs.edit().remove("item_" + i + "_haspic").commit();

        // decrease indices
        for(int j = i + 1; j <= getItemCount(c) + 1; j++)
        {
            int back = j - 1;
            prefs.edit().putString("item_" + back + "_uuid", prefs.getString("item_" + j  + "_uuid", "")).commit();
            prefs.edit().putString("item_" + back + "_ref", prefs.getString("item_" + j  + "_ref", "")).commit();
            prefs.edit().putFloat("item_" + back + "_price", prefs.getFloat("item_" + j  + "_price", (float) 0.0)).commit();
            prefs.edit().putBoolean("item_" + back + "_info", prefs.getBoolean("item_" + j  + "_haspic", false)).commit();
        }
    }
}
