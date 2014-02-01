package com.littleflash.pojo;


public class Item
{
    private String uuid;
    private String ref;
    private double price;
    private String info;
    private boolean isChecked;
    private boolean hasPic;
    
	public Item(String uuid, String ref, double price, String info, boolean hasPic)
    {
        this.uuid = uuid;
        this.ref = ref;
        this.price = price;
        this.info = info;
        this.hasPic(hasPic);
        this.isChecked = false;
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

	public boolean hasPic() {
		return hasPic;
	}

	public void hasPic(boolean hasPic) {
		this.hasPic = hasPic;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
