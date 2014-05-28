package com.dogar.geodesic.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.PolygonArea;
import net.sf.geographiclib.PolygonResult;
import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dogar.geodesic.R;
import com.dogar.geodesic.sync.FeedContract;
import com.dogar.geodesic.sync.FeedProvider;
import com.dogar.geodesic.sync.SyncAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by lester on 3/22/14.
 */
public class GoogleMapFragment extends Fragment {
	private final String PERIMETER = "Perimeter:";
	private final String AREA = "Area:";
	private final String METERS = " meters";
	private final String IN_SQUARE = "^2";
	private final PolygonArea polygonArea = new PolygonArea(Geodesic.WGS84,
			false);
	private final List<LatLng> pointsOfPolygons = new ArrayList<LatLng>();
	private final List<Marker> pins = new ArrayList<Marker>();
	private final List<Polygon> polygons = new ArrayList<Polygon>();

	private final List<Marker> points = new ArrayList<Marker>();
	private PolygonOptions polygonOptions;
	private MarkerOptions pinOptions;
	private TextView areaLabel;
	private TextView perimeterLabel;
	private GoogleMap googleMap;

	private SharedPreferences settings;
	private String accountName;
	private int pinCounter;
	private LatLng bufferPoint = new LatLng(0, 0);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.google_map_layout, container,
				false);
		MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		googleMap = fm.getMap();
		googleMap.setMyLocationEnabled(true);
		setMapLongClickListener();
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		settings = getActivity().getSharedPreferences("Geodesic", 0);
		setMapClickListener();
		drawMarkersFromLocal();
		areaLabel = (TextView) getView().findViewById(R.id.area_info);
		perimeterLabel = (TextView) getView().findViewById(R.id.perim_info);
	}

	public void clearPins() {
		for (Marker pin : pins)
			pin.remove();
		pins.clear();
		for (Polygon polygon : polygons)
			polygon.remove();
		polygons.clear();
		polygonArea.Clear();
		polygonOptions = null;
		pinOptions = null;
		pinCounter = 0;
		bufferPoint = new LatLng(0, 0);
		pointsOfPolygons.clear();
		areaLabel.setText(AREA + 0 + METERS + IN_SQUARE);
		perimeterLabel.setText(PERIMETER + 0 + METERS);
	}

	public void clearMapForNewUser() {
		clearPins();
		for (Marker point : points)
			point.remove();
		points.clear();
		drawMarkersFromLocal();
	}

	public GoogleMap getMap() {
		return googleMap;
	}

	private void drawMarkersFromLocal() {
		this.accountName = settings.getString("ACCOUNT_NAME", null);
		Cursor c = getActivity().getContentResolver().query(
				FeedContract.Entry.CONTENT_URI, SyncAdapter.PROJECTION,
				SyncAdapter.ACCOUNT_FILTER, new String[] { accountName }, null);
		while (c.moveToNext()) {
			Double latitude = c.getDouble(SyncAdapter.COLUMN_LATITUDE);
			Double longitude = c.getDouble(SyncAdapter.COLUMN_LONGITUDE);
			Long dateOfInsert = c.getLong(SyncAdapter.COLUMN_DATE_OF_INSERT);
			String title = c.getString(SyncAdapter.COLUMN_TITLE);
			String info = c.getString(SyncAdapter.COLUMN_INFO);
			drawMarker(new LatLng(latitude, longitude), title, info + " "
					+ new Date(dateOfInsert));
		}
	}

	private void setMapClickListener() {
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				ContentValues mNewValues = new ContentValues();
				mNewValues.putNull(FeedContract.Entry.COLUMN_NAME_POINT_ID);
				mNewValues.put(FeedContract.Entry.COLUMN_NAME_LATITUDE,
						point.latitude);
				mNewValues.put(FeedContract.Entry.COLUMN_NAME_LONGITUDE,
						point.longitude);
				mNewValues.put(FeedContract.Entry.COLUMN_NAME_DATE_OF_INSERT,
						new Date().getTime());
				mNewValues.put(FeedContract.Entry.COLUMN_NAME_TITLE,
						"Some point");
				mNewValues
						.put(FeedContract.Entry.COLUMN_NAME_INFO, "Some info");
				mNewValues.put(FeedContract.Entry.COLUMN_NAME_ACCOUNT,
						accountName);
				Uri mNewUri = getActivity().getContentResolver().insert(
						FeedContract.Entry.CONTENT_URI, mNewValues);
				drawMarker(point, point.toString(), "No info");
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
				
			}

			@Override
			public void onMarkerDragEnd(Marker marker) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onMarkerDrag(Marker marker) {
				LatLng pos = marker.getPosition();
				marker.setSnippet(pos.toString());
				marker.showInfoWindow();
			}
		});
	}

	private void setMapLongClickListener() {
		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng point) {
				drawPinPolygon(point);
			}
		});
	}

	private void drawMarker(LatLng point, String title, String info) {
		MarkerOptions markerOptions = new MarkerOptions();
		BitmapDescriptor icon = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_marker);
		markerOptions.position(point);
		markerOptions.draggable(true);
		markerOptions.anchor(0.5f, 1.0f);// center bottom of marker icon
		markerOptions.icon(icon);
		markerOptions.title(title);
		markerOptions.snippet(info);
		points.add(googleMap.addMarker(markerOptions));
	}

	private void drawPinPolygon(LatLng point) {
		pinCounter++;
		createPinOptions();
		pinOptions.title(point.toString());
		pinOptions.position(point);
		pins.add(googleMap.addMarker(pinOptions));

		createPolygonOptions();
		polygonOptions.add(point);
		polygons.add(googleMap.addPolygon(polygonOptions));
		pointsOfPolygons.add(point);

		polygonArea.AddPoint(point.latitude, point.longitude);
		PolygonResult rez = polygonArea.Compute();
		if (pinCounter > 2) {
			areaLabel.setText(AREA + Math.round(rez.area) + METERS + IN_SQUARE);
			perimeterLabel.setText(PERIMETER + Math.round(rez.perimeter)
					+ METERS);
		} else if (pinCounter == 2) {
			perimeterLabel.setText(PERIMETER
					+ Math.round(Geodesic.WGS84.Inverse(bufferPoint.latitude,
							bufferPoint.longitude, point.latitude,
							point.longitude).s12) + METERS);
			bufferPoint = point;
		} else if (pinCounter == 1) {
			bufferPoint = point;
		}
	}

	private void createPinOptions() {
		if (this.pinOptions == null) {
			this.pinOptions = new MarkerOptions();
			BitmapDescriptor icon = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_action);
			pinOptions.draggable(false);
			pinOptions.anchor(0.5f, 1.0f);// center bottom of marker icon
			pinOptions.icon(icon);
		}
	}

	private void createPolygonOptions() {
		if (this.polygonOptions == null) {
			this.polygonOptions = new PolygonOptions().strokeColor(Color.RED)
					.strokeWidth(2.0f)
					.fillColor(Color.argb(127, 139, 137, 137)).geodesic(true);
		}
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
					}
				});
		alert.show();
	}
}