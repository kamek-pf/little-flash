package com.littleflash.pojo;


public class Item
{

    private String uuid;
    private String ref;
    private double price;
    private String info;
    
	public Item(String uuid, String ref, double price, String info)
    {
        this.uuid = uuid;
        this.ref = ref;
        this.price = price;
        this.info = info;
    }

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
