package com.dogar.geodesic.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dogar.geodesic.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lester on 3/22/14.
 */
public class GoogleMapFragment extends Fragment {
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.google_map_layout, container, false);
        setMapClickListener();
        return rootView;
    }

    private void setMapClickListener() {
        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        googleMap = fm.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                drawMarker(point);
            }
        });
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
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
                marker.setSnippet("Lat.: "+pos.latitude + ",Long.: " + pos.longitude);
                marker.showInfoWindow();
            }
        });
    }

    private void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
        markerOptions.position(point);
        markerOptions.draggable(true);
        markerOptions.anchor(0.5f, 1.0f);//center bottom of marker icon
        markerOptions.icon(icon);
        markerOptions.title("Info about point");
        markerOptions.snippet("Lat.: "+Double.toString(point.latitude) + ",Long.: " + Double.toString(point.longitude));
        googleMap.addMarker(markerOptions);
    }

    /**
     * @return entered message as a String
     */
    private void openInputWindow(final Marker marker) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Title");
        alert.setMessage("Message");

        // Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                marker.setSnippet( input.getText().toString());
                marker.showInfoWindow();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }
}