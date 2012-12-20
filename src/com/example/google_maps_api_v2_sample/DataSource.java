package com.example.google_maps_api_v2_sample;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.net.ParseException;

public class DataSource {
	
	// API key, using for Google Places API access
	private static String API_KEY = "AIzaSyCQCcXeiEyccOqcNGfN5E-HjADFNxkVEbU";
	// Types for query, avaliable in Google Places API
	private static ArrayList<String> source = new ArrayList<String>(
			Arrays.asList("accounting", "airport", "amusement_park",
					"aquarium", "art_gallery", "atm", "bakery", "bank", "bar",
					"beauty_salon", "bicycle_store", "book_store",
					"bowling_alley", "bus_station", "cafe", "campground",
					"car_dealer", "car_rental", "car_repair", "car_wash",
					"casino", "cemetery", "church", "city_hall",
					"clothing_store", "convenience_store", "courthouse",
					"dentist", "department_store", "doctor", "electrician",
					"electronics_store", "", "embassy", "establishment",
					"finance", "fire_station", "florist", "food",
					"funeral_home", "furniture_store", "gas_station",
					"general_contractor", "grocery_or_supermarket", "gym",
					"hair_care", "hardware_store", "health", "hindu_temple",
					"home_goods_store", "hospital", "insurance_agency",
					"jewelry_store", "laundry", "lawyer", "library",
					"liquor_store", "local_government_office", "locksmith",
					"lodging", "meal_delivery", "meal_takeaway", "mosque",
					"movie_rental", "movie_theater", "", "moving_company",
					"museum", "night_club", "painter", "park", "parking",
					"pet_store", "pharmacy", "physiotherapist",
					"place_of_worship", "plumber", "police", "post_office",
					"real_estate_agency", "restaurant", "roofing_contractor",
					"rv_park", "school", "shoe_store", "shopping_mall", "spa",
					"stadium", "storage", "store", "subway_station",
					"synagogue", "taxi_stand", "train_station",
					"travel_agency", "university", "veterinary_care", "zoo"));

	public static ArrayList<String> getSource() {
		return source;
	}

	public static String getAPIkey() {
		return API_KEY;
	}

//	public String performSearch(final String types, final double lon,
//			final double lat, int radius) throws ParseException, IOException,
//			URISyntaxException {
//
//		StringBuilder sb = new StringBuilder(PLACES_API_BASE
//				+ TYPE_AUTOCOMPLETE + OUT_JSON);
//		sb.append("?sensor=false&key=" + API_KEY);
//		sb.append("&components=country:uk");
//		sb.append("&input=" + URLEncoder.encode(types, "utf8"));
//
//		final URI uri = new URIsetScheme("https")
//				.setHost("maps.googleapis.com").setPath(
//						"/maps/api/place/search/json");
//		// final URI uri = new
//		// URIsetScheme("https").setHost("maps.googleapis.com").setPath("/maps/api/place/search/json");
//
//		// builder.addParameter("location", lat + "," + lon);
//		// builder.addParameter("radius", "5");
//		// builder.addParameter("types", types);
//		// builder.addParameter("sensor", "true");
//		// builder.addParameter("key", key_API);
//
//		final HttpUriRequest request = new HttpGet(uri);
//
//		final HttpResponse execute = this.client.execute(request);
//
//		final String response = EntityUtils.toString(execute.getEntity());
//
//		return response;
//	}
}
