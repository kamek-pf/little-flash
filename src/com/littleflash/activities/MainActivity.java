/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.littleflash.activities;

import java.util.Date;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.api.client.util.DateTime;
import com.littleflash.pojos.DataStoreHelper;
import com.littleflash.pojos.QRData;


public class MainActivity extends Activity {

	private QRData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		data = new QRData();
		data.setShopId("0x00DOGESHOP");
		data.setItemId("0x00HOLYDOGE");
		data.setItemType("doge sdkjcnzk");
		data.setPrice("such price");
		data.setFlashDate(new DateTime(new Date()));

		new SendThread().execute();

	}
	private class SendThread extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... param) 
		{  
			DataStoreHelper dataStore = new DataStoreHelper(data);
			dataStore.sendData();
			return null;
		}
	}
}