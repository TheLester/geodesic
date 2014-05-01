package com.dogar.geodesic.fragments;

import java.io.IOException;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dogar.geodesic.CloudEndpointUtils;
import com.dogar.geodesic.R;
import com.dogar.geodesic.geopointinfoendpoint.Geopointinfoendpoint;
import com.dogar.geodesic.geopointinfoendpoint.Geopointinfoendpoint.ListGeoPointInfo;
import com.dogar.geodesic.geopointinfoendpoint.model.CollectionResponseGeoPointInfo;
import com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;

/**
 * Created by lester on 3/22/14.
 */
public class GoogleMapFragment extends Fragment {
	private GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.google_map_layout, container,
				false);
		setMapClickListener();
		new ShowGeoPoints().execute(getActivity().getApplicationContext());
		return rootView;
	}

	private void setMapClickListener() {
		MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);

		googleMap = fm.getMap();
		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				drawMarker(point);
				new CreateAndSaveGeoPoint().execute(point.latitude,
						point.longitude);
			}
		});
		googleMap
				.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick(Marker marker) {
						openInputWindow(marker);

					}
				});
		googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

			@Override
			public void onMarkerDragStart(Marker marker) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onMarkerDragEnd(Marker marker) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onMarkerDrag(Marker marker) {
				LatLng pos = marker.getPosition();
				marker.setSnippet("Lat.: " + pos.latitude + ",Long.: "
						+ pos.longitude);
				marker.showInfoWindow();
			}
		});
	}

	private void drawMarker(LatLng point) {
		MarkerOptions markerOptions = new MarkerOptions();
		BitmapDescriptor icon = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_marker);
		markerOptions.position(point);
		markerOptions.draggable(true);
		markerOptions.anchor(0.5f, 1.0f);// center bottom of marker icon
		markerOptions.icon(icon);
		markerOptions.title("Info about point");
		markerOptions.snippet("Lat.: " + Double.toString(point.latitude)
				+ ",Long.: " + Double.toString(point.longitude));
		googleMap.addMarker(markerOptions);
	}

	private void openInputWindow(final Marker marker) {

		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("Title");
		alert.setMessage("Message");

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				marker.setSnippet(input.getText().toString());
				marker.showInfoWindow();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		alert.show();
	}

	public class CreateAndSaveGeoPoint extends AsyncTask<Double, Integer, Long> {
		@Override
		protected Long doInBackground(Double... coords) {

			Geopointinfoendpoint.Builder endpointBuilder = new Geopointinfoendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			Geopointinfoendpoint endpoint = CloudEndpointUtils.updateBuilder(
					endpointBuilder).build();
			try {
				GeoPointInfo geoPointInfo = new GeoPointInfo();
				geoPointInfo.setLatitude(coords[0]);
				geoPointInfo.setLongitude(coords[1]);
				geoPointInfo.setInsertDate(new DateTime(new Date()));
				
				GeoPointInfo result = endpoint.insertGeoPointInfo(geoPointInfo)
						.execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}

	public class ShowGeoPoints extends AsyncTask<Context, Integer, Long> {
		@Override
		protected Long doInBackground(Context... contexts) {

			Geopointinfoendpoint.Builder endpointBuilder = new Geopointinfoendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					});
			Geopointinfoendpoint endpoint = CloudEndpointUtils.updateBuilder(
					endpointBuilder).build();
			try {
				CollectionResponseGeoPointInfo listOfPoints = endpoint
						.listGeoPointInfo().execute();
				if (!listOfPoints.isEmpty()) {
					Handler handler = new Handler(Looper.getMainLooper());

					for (final GeoPointInfo geoPointInfo : listOfPoints
							.getItems())

						handler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								drawMarker(new LatLng(geoPointInfo
										.getLatitude(), geoPointInfo
										.getLongitude()));
							}
						});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (long) 0;
		}
	}
}