/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dogar.geodesic.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;

import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dogar.geodesic.R;
import com.dogar.geodesic.adapters.NavDrawerListAdapter;
import com.dogar.geodesic.fragments.GoogleMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class StartActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private String[] mHeaders;
    private String[] mapTypeTitles;
    private String[] helpTitles;

    private TypedArray mIcons;

    private static int itemMenuPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        mIcons = getResources().obtainTypedArray(R.array.nav_map_type_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        NavDrawerListAdapter mAdapter = new NavDrawerListAdapter(this);
        mHeaders = getResources().getStringArray(R.array.menu_headers);
        /*******************Map View*********************/
        mapTypeTitles = getResources().getStringArray(R.array.map_types);
        mAdapter.addHeader(mHeaders[0]);
        int i;
        for (i = 0; i < mapTypeTitles.length; i++)
            mAdapter.addItem(mapTypeTitles[i], mIcons.getResourceId(i, -1));
        mIcons.recycle();
        /*******************About*********************/
        mAdapter.addHeader(mHeaders[1]);
        helpTitles = getResources().getStringArray(R.array.help_items);
        mAdapter.addItem(helpTitles[0], mIcons.getResourceId(i++, -1));
        /*******************************************************/

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(mAdapter);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
                mDrawerLayout.setScrimColor(Color.TRANSPARENT);
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            setGMFragment();
            mDrawerList.setSelection(1);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (itemMenuPosition != position) selectItem(position);
        }
    }

    private void setGMFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        GoogleMapFragment fragment = new GoogleMapFragment();
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
    }

    private void selectItem(int position) {

        switch (position) {
            case 1:
                setMapType(GoogleMap.MAP_TYPE_TERRAIN, position);
                break;
            case 2:
                setMapType(GoogleMap.MAP_TYPE_NORMAL, position);
                break;
            case 3:
                setMapType(GoogleMap.MAP_TYPE_HYBRID, position);
                break;
            case 5:
                showAboutDialogWindow();
                break;
            default:
                break;
        }
    }

    private void setMapType(int mapType, int position) {
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        fragment.getMap().setMapType(mapType);
        updateItemAndCloseDrawer(position);
        itemMenuPosition = position;
    }

    private void updateItemAndCloseDrawer(int position) {
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        setTitle(mapTypeTitles[position - 1]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void showAboutDialogWindow() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("Geodesic")
                .setMessage(
                        Html.fromHtml("Geodesic Application.Developed by:"
                                + " <i>Dmitri Dogar (AE-101)</i>. "
                                + "<br><b><u>Contact Information:</u></b>"
                                + "<br><a href=\"mailto:dimsdevelop@gmail.com\">Author's Em@il</a>"
                                +  "<br><br>Open source at<br><a href=\"https://github.com/TheLester\">GitHub Page</a>")
                )
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                                final int id) {
                            }
                        }
                );
        final AlertDialog alert = builder.create();
        alert.show();
        ((TextView) alert.findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }
}