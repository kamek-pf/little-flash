package com.littleflash.pojo;


public class QRData {

    private String itemId;
    private String itemName;
    private double price;
    private String itemInfo;

    public void process(String flash)
    {
        String[] part = flash.split("#");

        this.itemId = part[1];
        this.itemName = part[2];
        this.price = Double.parseDouble(part[3]);
        this.itemInfo = part[4];
    }

    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getItemInfo() {
        return itemInfo;
    }
    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }
}
