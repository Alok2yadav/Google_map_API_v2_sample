package com.example.google_maps_api_v2_sample;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.CameraPositionCreator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MainActivity extends android.support.v4.app.FragmentActivity {

	private static GoogleMap mMap;
	private LatLng myLocationLatLng;
	String value;
	static ArrayList<MarkerOptions> markerHeap = new ArrayList<MarkerOptions>();

	// static List<MarkerOptions> markerHeap = new ArrayList<MarkerOptions>();

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

	}

	@Override
	protected void onStart() {
		if (!markerHeap.isEmpty()) {
			for (MarkerOptions marker : markerHeap) {
				mMap.addMarker(marker).setDraggable(true);
			}
		}

		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		// mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setAllGesturesEnabled(true);

		mMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng position) {

				setMarkerDescription(position);
				// MarkerOptions marker =new MarkerOptions().position(arg0) ;

			}
		});

		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				// setDescription(marker);
				marker.showInfoWindow();
				return false;
			}
		});

		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Location myLocation = mMap.getMyLocation();

						myLocationLatLng = new LatLng(49, 32);
						// myLocationLatLng = new
						// LatLng(myLocation.getLatitude(),
						// myLocation.getLongitude());
						// TO-DO mMap.clear();
						// GoogleMapOptions options = new GoogleMapOptions();
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(myLocationLatLng) // Sets the center of
															// the map to
															// Mountain View
								.zoom(17) // Sets the zoom
								.bearing(0) // Sets the orientation of the
											// camera to east
								.tilt(0) // Sets the tilt of the camera to 30
											// degrees
								.build(); // Creates a CameraPosition from the
											// builder
						mMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					}
				});

	}

	void setMarker (LatLng position, int color, String title) {
//		int title = markerHeap.size() + 1;
//		int color = (title * 10) % 360;
		markerHeap.add(new MarkerOptions().position(position)
				.title(title)
				.icon(BitmapDescriptorFactory.defaultMarker(color)));

		mMap.addMarker(markerHeap.get(markerHeap.size() - 1));

	}

	public String setMarkerDescription(final LatLng position) {

		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

		final EditText input = new EditText(MainActivity.this);
		final NumberPicker numberPiker = new NumberPicker(MainActivity.this);
		final TextView secondTitle = new TextView(MainActivity.this);
		secondTitle.setText("Set color");
		
		numberPiker.setMinValue(0);
		numberPiker.setMaxValue(360);

		
		 LinearLayout root = new LinearLayout(MainActivity.this);
		 
		 root.setOrientation(LinearLayout.VERTICAL);
		 root.addView(input);
		 root.addView(secondTitle);
		 root.addView(numberPiker);
		
		
		value = null;
		alert.setTitle("Add new Marker")
				.setMessage("Add description")
				.setView(root)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
//						marker.setTitle(input.getText().toString());
						setMarker(position, numberPiker.getValue(),input.getText().toString());
						// return value.toString();
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.

							}
						}).show();
		return value;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
