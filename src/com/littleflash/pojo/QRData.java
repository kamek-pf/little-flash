package com.littleflash.pojo;

import com.google.api.client.util.DateTime;

public class QRData {
	
	private String shopName;
    private String shopId;
	private String itemId;
	private String itemName;
	private String itemType;
    private String price;
    private DateTime flashDate;
    
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
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
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public DateTime getFlashDate() {
		return flashDate;
	}
	public void setFlashDate(DateTime flashDate) {
		this.flashDate = flashDate;
	}
}
