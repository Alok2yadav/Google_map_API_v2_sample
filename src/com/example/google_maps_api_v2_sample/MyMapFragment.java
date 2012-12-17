package com.example.google_maps_api_v2_sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MyMapFragment extends SupportMapFragment {
	private SupportMapFragment smFragment;
	private GoogleMap mMap;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		smFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapFragment1);
		mMap = smFragment.getMap();
//		mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//		mMap.setMyLocationEnabled(true);
////		
		
		
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		public GoogleMap getMap_() {
//		return ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMap();
//		smFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapFragment1));
		return mMap;
		}
	
	public MyMapFragment getInstance() {
		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
		super.onCreateView(inflater, viewGroup, savedInstanceState);
//		Instance = this;
		return inflater.inflate(R.layout.map_fragment, viewGroup, false);
	}

}
