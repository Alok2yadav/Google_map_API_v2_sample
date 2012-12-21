package com.example.google_maps_api_v2_sample;

import com.example.google_maps_api_v2_sample.DataSource.Types;
import com.google.android.gms.maps.model.LatLng;

//Nearby search queries

public class GooglePlacesQueryBuilder {

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
	// private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json?";

	private static final String LOCATION = "location=";
	private static final String RADIUS = "&radius=";
	private static final String NEXT_PAGE_TOKEN = "&pagetoken=";
	private static final String KEY_WORDS = "&keyword=";
	private static final String TYPES = "&types=";
	private static final String NAME = "&name=";
	private static final String SENSOR_FALSE = "&sensor=false";
	private static final String SENSOR_TRUE = "&sensor=true";
	private static final String KEY = "&key=";

	// String query;

	String location;
	String radius;
	String pagetoken;
	String types;
	

	String name;
	String keywords;
	boolean sensor;
	String keyAPI;

	GooglePlacesQueryBuilder(LatLng latlng, int radius) {
		// query = PLACES_API_BASE + OUT_JSON;
		location = Double.toString(latlng.latitude) + "."
				+ Double.toString(latlng.longitude);
		keyAPI = KEY + DataSource.getAPIkey();
//Default value of sensor		
		sensor = true;
	}

	public void pagetoken(String pagetoken) {
		this.pagetoken = pagetoken;
	}
	public GooglePlacesQueryBuilder sensor(boolean outerSensor) {
		sensor = outerSensor;
		return this;

	}

	public GooglePlacesQueryBuilder type(String type) {

		if (types == null) {
			types = type;
		} else {
			types += ("|" + type);
		}

		return this;

	}

	public GooglePlacesQueryBuilder keyword(String keyword) {

		if (keywords == null) {
			keywords = keyword.trim().replace(" ", "+");
		} else {
			keywords += ("+" + keyword.trim().replace(" ", "+"));
		}

		return this;

	}

	// allow name set only once
	public GooglePlacesQueryBuilder name(String outerName) {

		if (name == null) {
			name = outerName.trim().replace(" ", "+");
		}

		return this;

	}

	public Boolean isQueryValid() {
		// TODO query validation check
		return true;
	}

	public String build() {
		String resultQuery = null;
		location = LOCATION + location;
		radius = RADIUS + radius;

		if (keywords == null) {
			keywords = "";
		} else {
			keywords = KEY_WORDS + keywords;
		}

		if (types == null) {
			types = "";
		} else {
			types = TYPES + types;
		}

		if (name == null) {
			name = "";
		} else {
			name = NAME + name;
		}

		if (pagetoken == null){
			pagetoken = "";
		}else
		{
			pagetoken = NEXT_PAGE_TOKEN+pagetoken;
		}
		
		String stringSensor;
		if (sensor) {
			stringSensor = SENSOR_TRUE;
		} else {
			stringSensor = SENSOR_FALSE;
		}

		resultQuery = PLACES_API_BASE + OUT_JSON + location + radius + keywords
				+ types + name + stringSensor +pagetoken+ keyAPI;

		return resultQuery;
	}
}
