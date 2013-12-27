package com.littleflash.pojos;

import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.littleflash.backend.flashendpoint.Flashendpoint;
import com.littleflash.backend.flashendpoint.model.Flash;

public class DataStoreHelper {
    
	private QRData data;
	
	public DataStoreHelper(QRData data){
		this.setData(data);
	}
	
	public void sendData(){
		Flash flash = new Flash();
		
		flash.setShopId(data.getShopId());
		flash.setItemId(data.getItemId());
		flash.setItemType(data.getItemType());
		flash.setPrice(data.getPrice());
		flash.setFlashDate(data.getFlashDate());
		
		Flashendpoint.Builder builder = new Flashendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        null);
		          
		builder = CloudEndpointUtils.updateBuilder(builder);

		Flashendpoint endpoint = builder.build();
		try {
			endpoint.insertFlash(flash).execute();
		} 
		catch (IOException e) {
		        e.printStackTrace();
		}
	}

	public QRData getData() {
		return data;
	}

	public void setData(QRData data) {
		this.data = data;
	}
    
}
