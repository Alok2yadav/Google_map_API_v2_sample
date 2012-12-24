package com.example.google_maps_api_v2_sample;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class DataProvider {

	private static final String STATUS_KEY = "status";
	private static final String RESULTS_KEY = "results";
	// geometry key
	private static final String GEOMETRY_KEY = "geometry";
	// location key to obtain particular place geolocation coordinates.
	private static final String LOCATION_KEY = "location";
	// latitiude key
	private static final String LATITUDE_KEY = "lat";
	// longitude key
	private static final String LONGITUDE_KEY = "lng";
	// id key of particular place.
	private static final String PLACE_ID_KEY = "id";
	// name key of particular place.
	private static final String PLACE_NAME_KEY = "name";
	// raiting key of parricular place which is optional parameter in JSON
	// response.
	private static final String RAITING_KEY = "rating";
	// NextPage token
	private static final String NEXT_PAGE_TOKEN = "next_page_token";
	ArrayList<Place> places;
	private String LOG_TAG = "queryString";

	public static boolean isOnline() {
		Context context = MainActivity.getAppContext();
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public ArrayList<Place> geetFeed(GooglePlacesQueryBuilder query)
			throws Exception {
		places = new ArrayList<Place>();
		JSONObject jsonData = null;
//		String pagetokenString;
		do {
//			pagetokenString = null;
			Log.d(LOG_TAG, "before query building");
			String queryString = query.build();
			Log.d(LOG_TAG, queryString);
			String JsonLoad = JsonLoader.makeGetRequest(queryString);

			// Log.d(LOG_TAG , "jsonData " +
			// jsonData.getString(NEXT_PAGE_TOKEN));
			jsonData = new JSONObject(JsonLoad);

			String status = jsonData.getString(STATUS_KEY);
			Log.d(LOG_TAG, "Status.valueOf(status)" + Status.valueOf(status));
			if (jsonData.has(NEXT_PAGE_TOKEN)){
			//pagetokenString = jsonData.getString(NEXT_PAGE_TOKEN);
			query.pagetoken(jsonData.getString(NEXT_PAGE_TOKEN));
			Log.d(LOG_TAG, "NEXT_PAGE_TOKEN " + jsonData.getString(NEXT_PAGE_TOKEN));
			}
//			if (jsonData.has(NEXT_PAGE_TOKEN)) {
//				pagetokenString = jsonData.getString(NEXT_PAGE_TOKEN);
//			} else {
//				pagetokenString = null;
//			}
			
			// validate response before parsing.
			switch (Status.valueOf(status)) {
			case REQUEST_DENIED:
			case INVALID_REQUEST:
				throw new Exception(
						"Some of required parameter were missed. Check the service URL.");
			case OVER_QUERY_LIMIT:
				throw new Exception(
						"Free usage plan exceed the number of requests. Upgrade your plan.");
			case OK:
				places.addAll(parseResults(jsonData.getJSONArray(RESULTS_KEY)));
				break;
			case ZERO_RESULTS:
				places.addAll(new ArrayList<Place>());
				break;
			default:
				// should never occurs if server is not changed.
				throw new IllegalStateException("Status is not supported.");
			}
			// Log.d(LOG_TAG , "jsonData 2" +
			// jsonData.getString(NEXT_PAGE_TOKEN));
//			if (jsonData.has(NEXT_PAGE_TOKEN)) {
//				query.pagetoken(jsonData.getString(NEXT_PAGE_TOKEN));
//			} else {
//				break;
//			}
			if (jsonData.has(NEXT_PAGE_TOKEN)){Thread.sleep(3000);}
		} while (jsonData.has(NEXT_PAGE_TOKEN));
		return places;

	}

	// parsed Place results from JSON.
	private static ArrayList<Place> parseResults(JSONArray jsonResults)
			throws JSONException {
		int placesSize = jsonResults.length();
		ArrayList<Place> placesResult = new ArrayList<Place>(placesSize);
		Place place;
		for (int i = 0; i < placesSize; i++) {

			JSONObject jsonPlace = jsonResults.getJSONObject(i);
			JSONObject jsonLocation = jsonPlace.getJSONObject(GEOMETRY_KEY)
					.getJSONObject(LOCATION_KEY);
			LatLng location = new LatLng(jsonLocation.getDouble(LATITUDE_KEY),
					jsonLocation.getDouble(LONGITUDE_KEY));
			String id = (jsonPlace.getString(PLACE_ID_KEY));
			String name = (jsonPlace.getString(PLACE_NAME_KEY));

			place = new Place(location, name);
			placesResult.add(place);
		}
		return placesResult;
	}

	private static enum Status {
		OK, ZERO_RESULTS, OVER_QUERY_LIMIT, REQUEST_DENIED, INVALID_REQUEST
	}

}
