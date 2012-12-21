package com.example.google_maps_api_v2_sample;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA. User: boss1088 Date: 11/12/12 Time: 9:39 PM To
 * change this template use File | Settings | File Templates.
 */
public class JsonLoader 
{

	private static final String LOG_TAG = "JsonLoader_";

	public static String makeGetRequest(String request) throws NetworkOnMainThreadException  {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		Log.d(LOG_TAG, "before request " + request);
		HttpGet httpGet = new HttpGet(request);

		HttpResponse response;
		try {
			response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				Log.d(LOG_TAG, "statusCode " + statusCode);
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d(LOG_TAG, "Server respond is " + statusCode);
			}
		} catch (ClientProtocolException e) {
			Log.d(LOG_TAG, "ClientProtocolException e " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(LOG_TAG, "IOException e " + e.getMessage());
			e.printStackTrace();
		}
		
		
		String respond = builder.toString();
		Log.d(LOG_TAG, "Server respond is " + respond);
		return respond;
	}
}