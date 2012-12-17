package com.example.google_maps_api_v2_sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {

	private GoogleMap mMap;
	private MyMapFragment smFragment;
	private FragmentTransaction smfTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		// fManager = (SupportMapFragment)
		// getSupportFragmentManager().findFragmentById(R.id.mapFragment1);
		// mMap = (fManager).getMap();
		// mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		// mMap.setMyLocationEnabled(true);
		// mMap.set

		findViewById(R.id.showMap).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						showMap();
						v.setVisibility(View.INVISIBLE);

					}
				});
		findViewById(R.id.button2).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mMap = smFragment.getMap();
						mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						mMap.setMyLocationEnabled(true);
					}
				});

	}

	private void showMap() {
//		if (smFragment != null) {
////			smfTransaction.disallow();
//			smfTransaction.detach(smFragment);
//			smFragment = null;
//		} else {
			smfTransaction = getSupportFragmentManager().beginTransaction();
			smFragment = new MyMapFragment();
			smfTransaction.replace(R.id.mapFragmentNest1, smFragment);
			smfTransaction.commit();
//		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
