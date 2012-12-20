package com.example.google_maps_api_v2_sample;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MarkerOptionsCreator;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("NewApi")
public class MainActivity extends android.support.v4.app.FragmentActivity {

	private static GoogleMap mMap;
	private LatLng myLocationLatLng;
	String value;
	static CameraPosition currentCameraPosition;
	static ArrayList<MarkerOptions> markerHeap = new ArrayList<MarkerOptions>();
	static ArrayList<String> markerIdHeap = new ArrayList<String>();

	// static List<MarkerOptions> markerHeap = new ArrayList<MarkerOptions>();
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		currentCameraPosition = mMap.getCameraPosition();
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		
		super.onStart();
	}

	class CustomInfoWindowAdapter2 implements InfoWindowAdapter {
		private LinearLayout root;

		CustomInfoWindowAdapter2() {

		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;

		}

		@Override
		public View getInfoContents(Marker marker) {
			root = new LinearLayout(MainActivity.this);

			root.setOrientation(LinearLayout.VERTICAL);

			TextView title = new TextView(MainActivity.this);

			TextView spinnet = new TextView(MainActivity.this);
			ImageView image = new ImageView(MainActivity.this);
			root.addView(spinnet);
			root.addView(title);

			root.addView(image);

			image.setImageResource(R.drawable.ic_launcher);

			Log.i("getInfoContents",
					marker.getTitle() + " " + marker.getTitle());
			spinnet.append(marker.getSnippet());
			title.append(marker.getTitle());

			return root;

		}

	}

	class CustomInfoWindowAdapter implements InfoWindowAdapter {

		private final View mWindow;
		private final View mContents;

		CustomInfoWindowAdapter() {
			mWindow = getLayoutInflater().inflate(R.layout.custom_info_window,
					null);
			mContents = getLayoutInflater().inflate(
					R.layout.custom_info_contents, null);
		}

		@Override
		public View getInfoWindow(Marker marker) {

			render(marker, mWindow);
			return mWindow;
		}

		@Override
		public View getInfoContents(Marker marker) {

			render(marker, mContents);
			return mContents;
		}

		private void render(Marker marker, View view) {
			int badge;

			((ImageView) view.findViewById(R.id.badge))
					.setImageResource(R.drawable.ic_launcher);

			String title = marker.getTitle();
			TextView titleUi = ((TextView) view.findViewById(R.id.title));
			if (title != null) {

				titleUi.setText(title);
			} else {
				titleUi.setText("");
			}

			String snippet = marker.getSnippet();
			TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
			if (snippet != null) {
				snippetUi.setText(snippet);
			} else {
				snippetUi.setText("");
			}
		}
	}

	@Override
	public void onBackPressed() {
		mMap = null;
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		if (savedInstanceState == null) {
			// First incarnation of this activity.
			mapFragment.setRetainInstance(true);

		} else {
			// Reincarnated activity. The obtained map is the same map instance
			// in the previous
			// activity life cycle. There is no need to reinitialize it.
			mMap = mapFragment.getMap();
		}

		setUpMapIfNeeded();
		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter2());

		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setAllGesturesEnabled(true);
		

		// Instantiates a new Polyline object and adds points to define a
		// rectangle
		PolylineOptions polylineRectOptions = new PolylineOptions()
				.add(new LatLng(37.39, -122.0)).add(new LatLng(37.47, -122.0)) // North
																				// of
																				// the
																				// previous
																				// point,
																				// but
																				// at
																				// the
																				// same
																				// longitude
				.add(new LatLng(37.45, -122.1)) // Same latitude, and 30km to
												// the west
				.add(new LatLng(37.37, -122.1)) // Same longitude, and 16km to
												// the south
				.add(new LatLng(37.39, -122.0)); // Closes the polyline.

		// Set the rectangle's color to red
		polylineRectOptions.color(Color.RED);
		polylineRectOptions.add(new LatLng(37.42, -122.0));

		// Get back the mutable Polyline
		Polyline polyline = mMap.addPolyline(polylineRectOptions);

		PolygonOptions polygonRectOptions = new PolygonOptions().add(
				new LatLng(37.35, -122.0), new LatLng(37.45, -122.0),
				new LatLng(37.45, -122.2), new LatLng(37.35, -122.2),
				new LatLng(37.35, -122.0));

		// Set the rectangle's stroke color to red
		polygonRectOptions.strokeColor(Color.RED);

		// Set the rectangle's fill to blue
		polygonRectOptions.fillColor(Color.BLUE);

		// Get back the mutable Polygon
		Polygon polygon = mMap.addPolygon(polygonRectOptions);
		ArrayList<LatLng> holePoints = new ArrayList<LatLng>(Arrays.asList(
				new LatLng(1, 1), new LatLng(1, 2), new LatLng(2, 2),
				new LatLng(2, 1), new LatLng(1, 1)));
		mMap.addPolygon(new PolygonOptions()
				.add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5),
						new LatLng(3, 0), new LatLng(0, 0)).addHole(holePoints)
				.fillColor(Color.BLUE));

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
				Toast.makeText(
						getApplication(),
						"Marker InfoWindow is shown "
								+ marker.isInfoWindowShown(), 0).show();
				// setDescription(marker);
				if (marker.isInfoWindowShown()) {

					marker.hideInfoWindow();
				} else {
					marker.showInfoWindow();

				}
				Toast.makeText(
						getApplication(),
						"Marker InfoWindow is shown "
								+ marker.isInfoWindowShown(), 0).show();
				return false;
			}
		});

		mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				deleteMarker(marker.getId());
				Toast.makeText(
						getApplication(),
						"Removed marker '" + marker.getTitle() + "'"
								+ marker.getId(), Toast.LENGTH_LONG).show();
				marker.remove();

			}
		});

		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						setCameraPosition(new LatLng(37.35, -122.0));

					}
				});

		findViewById(R.id.button2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						setCameraPosition(new LatLng(0, 0));

					}
				});

		findViewById(R.id.button3).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Location myLocation = mMap.getMyLocation();
						myLocationLatLng = new LatLng(myLocation.getLatitude(),
								myLocation.getLongitude());
						setCameraPosition(myLocationLatLng);

					}
				});
		findViewById(R.id.clear_map_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mMap.clear();
					}
				});
	}

	private void setCameraPosition(LatLng position) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(position) // Sets the center of
									// the map to
									// Mountain View
				.zoom(10) // Sets the zoom
				.bearing(0) // Sets the orientation of the
							// camera to east
				.tilt(0) // Sets the tilt of the camera to 30
							// degrees
				.build(); // Creates a CameraPosition from the
							// builder
		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

	}

	void setMarker(LatLng position, int color, String title) {
		// int title = markerHeap.size() + 1;
		// int color = (title * 10) % 360;
		markerHeap.add(new MarkerOptions().position(position).snippet("")
				.title(title)
				.icon(BitmapDescriptorFactory.defaultMarker(color)));

		markerIdHeap.add(mMap.addMarker(markerHeap.get(markerHeap.size() - 1)).getId());
		

	}
	
	void deleteMarker(String outerMarkerID){
		int position = 0;
		for (String innerMarkerid:markerIdHeap)
		{
			if (outerMarkerID.equals(innerMarkerid)){
				markerHeap.remove(position);
				markerIdHeap.remove(position);
				return;
				
			}
			position++;
		}
		
	}

	public String setMarkerDescription(final LatLng position) {

		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

		final EditText input = new EditText(MainActivity.this);
		final NumberPicker numberPiker = new NumberPicker(MainActivity.this);
		final TextView secondTitle = new TextView(MainActivity.this);
		secondTitle.setText("Set color");

		numberPiker.setMinValue(0);
		numberPiker.setMaxValue(36);

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
						// marker.setTitle(input.getText().toString());
						setMarker(position, numberPiker.getValue() * 10, input
								.getText().toString());
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

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(0, 0))
				.title("Marker1")
				.snippet("Marker 1 spinnet")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(10, 0))
				.title("Marker2")
				.snippet("Marker 2 spinnet")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(0, 10))
				.title("Marker3")
				.snippet("Marker 3 spinnet")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
		if (!markerHeap.isEmpty()) {
			for (MarkerOptions marker : markerHeap) {

				mMap.addMarker(marker);
			}
		}
		if (currentCameraPosition != null) {
			mMap.moveCamera(CameraUpdateFactory
					.newCameraPosition(currentCameraPosition));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
