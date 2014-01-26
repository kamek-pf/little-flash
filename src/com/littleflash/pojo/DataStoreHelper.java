package com.littleflash.pojo;

import java.io.IOException;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.littleflash.backend.flashendpoint.Flashendpoint;
import com.littleflash.backend.flashendpoint.model.Flash;

public class DataStoreHelper {
    
	private QRData data;
	private Context c;
	
	public DataStoreHelper(QRData data){
		this.setData(data);
	}
	
	public DataStoreHelper(QRData data, Context c){
		this.setData(data);
		this.c = c;
	}
	
	public void sendData(){
		Flash flash = new Flash();
		
		flash.setItemId(data.getItemId());
		flash.setUserEmail(EmailHandler.getEmail(c));
	
		
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
