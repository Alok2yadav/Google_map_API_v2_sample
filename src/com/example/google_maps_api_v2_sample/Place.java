package com.example.google_maps_api_v2_sample;

import java.net.URL;
import java.util.ArrayList;
import com.google.android.gms.maps.model.LatLng;

public class Place {
	
	LatLng location;
	LatLng northeastViewport;	
	LatLng southwestViewport;
	
	URL icon;
	
	String id;
	String name;
	
	String reference;
	
	ArrayList<String> types;
	
Place(LatLng outerLocation, String outerName)
{
	location=outerLocation;
	name=outerName;
}

public LatLng getNortheastViewport() {
	return northeastViewport;
}

public void setNortheastViewport(LatLng northeastViewport) {
	this.northeastViewport = northeastViewport;
}

public LatLng getSouthwestViewport() {
	return southwestViewport;
}

public void setSouthwestViewport(LatLng southwestViewport) {
	this.southwestViewport = southwestViewport;
}

public URL getIcon() {
	return icon;
}

public void setIcon(URL icon) {
	this.icon = icon;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getReference() {
	return reference;
}

public void setReference(String reference) {
	this.reference = reference;
}

public ArrayList<String> getTypes() {
	return types;
}

public void setTypes(ArrayList<String> types) {
	this.types = types;
}

public LatLng getLocation() {
	return location;
}

public String getName() {
	return name;
}
}
