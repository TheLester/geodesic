package com.dogar.geodesic.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dogar.geodesic.CloudEndpointUtils;
import com.dogar.geodesic.geopointinfoendpoint.Geopointinfoendpoint;
import com.dogar.geodesic.geopointinfoendpoint.model.CollectionResponseGeoPointInfo;
import com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo;
import com.dogar.geodesic.map.StartActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.common.collect.ArrayListMultimap;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static Geopointinfoendpoint.Builder endpointBuilder;
	private static Geopointinfoendpoint geopointInfoEndpoint;
	private static GoogleAccountCredential credential;
	public static final String ACCOUNT_FILTER = "account = ?";

	private  static final String TAG = "SyncAdapter";
	/**
	 * Content resolver, for performing database operations.
	 */
	private final ContentResolver mContentResolver;
	/**
	 * Project used when querying content provider. Returns all known fields.
	 */
	public static final String[] PROJECTION = new String[] {
			FeedContract.Entry._ID, FeedContract.Entry.COLUMN_NAME_POINT_ID,
			FeedContract.Entry.COLUMN_NAME_LATITUDE,
			FeedContract.Entry.COLUMN_NAME_LONGITUDE,
			FeedContract.Entry.COLUMN_NAME_DATE_OF_INSERT,
			FeedContract.Entry.COLUMN_NAME_TITLE,
			FeedContract.Entry.COLUMN_NAME_INFO,
			FeedContract.Entry.COLUMN_NAME_ACCOUNT };
	// Constants representing column positions from PROJECTION.
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_POINT_ID = 1;
	public static final int COLUMN_LATITUDE = 2;
	public static final int COLUMN_LONGITUDE = 3;
	public static final int COLUMN_DATE_OF_INSERT = 4;
	public static final int COLUMN_TITLE = 5;
	public static final int COLUMN_INFO = 6;

	private Account account;
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		mContentResolver = context.getContentResolver();

	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		this.account = account;
		updateLocalFeedData(syncResult);

	}

	private void updateLocalFeedData(SyncResult syncResult) {
		final ContentResolver contentResolver = getContext()
				.getContentResolver();

		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		List<Long> nonInsertIDs = new ArrayList<Long>();
		// Build hash table(with multiple keys-google collection) of incoming
		// entries
		/*
		 * ArrayListMultimap<Long, GeoPointInfo> entryMap = ArrayListMultimap
		 * .create(); for (GeoPointInfo e : entries) { entryMap.put(e.getId(),
		 * e); }
		 */

		// Get list of all items
		Log.i(TAG, "Fetching local entries for merge");
		Uri uri = FeedContract.Entry.CONTENT_URI; // Get all entries
		Cursor c = contentResolver.query(uri, PROJECTION, ACCOUNT_FILTER,
				new String[] { account.name }, null);
		assert c != null;
		Log.i(TAG, "Found " + c.getCount()
				+ " local entries. Computing merge solution...");

		if (c.getCount() == 0) {
			// first insert
			Log.i(TAG, "Getting from datastore");
			final List<GeoPointInfo> entries = getGeoPointsFromDataStore();
			Log.i(TAG, "Getting complete. Found " + entries.size() + " entries");
			for (GeoPointInfo g : entries) {
				Log.i(TAG, "Scheduling 1st insert: entry_id=" + g.getId());
				insertToBatch(batch,g);
				nonInsertIDs.add(g.getId());
				syncResult.stats.numInserts++;
			}
		}
		// Find stale data
		int id;
		Long pointId;
		Double latitude;
		Double longitude;
		Long dateOfInsert;
		String title;
		String info;

		while (c.moveToNext()) {
			syncResult.stats.numEntries++;
			id = c.getInt(COLUMN_ID);
			pointId = c.getLong(COLUMN_POINT_ID);
			latitude = c.getDouble(COLUMN_LATITUDE);
			longitude = c.getDouble(COLUMN_LONGITUDE);
			dateOfInsert = c.getLong(COLUMN_DATE_OF_INSERT);
			title = c.getString(COLUMN_TITLE);
			info = c.getString(COLUMN_INFO);

			if (pointId != 0) {
				nonInsertIDs.add(pointId);
				GeoPointInfo geoPointToUpdate = getGeoPointFromDataStore(pointId);
				// Check to see if the entry needs to be updated
				Uri existingUri = FeedContract.Entry.CONTENT_URI.buildUpon()
						.appendPath(Integer.toString(id)).build();
				if ((geoPointToUpdate.getLongitude() != null && !geoPointToUpdate
						.getLongitude().equals(longitude))
						|| (geoPointToUpdate.getLatitude() != null && !geoPointToUpdate
								.getLatitude().equals(latitude))
						|| (geoPointToUpdate.getTitleInfo() != null && !geoPointToUpdate
								.getTitleInfo().equals(title))
						|| (geoPointToUpdate.getTextInfo() != null && !geoPointToUpdate
								.getTextInfo().equals(info))) {
					// Update existing record
					Log.i(TAG, "Scheduling update: " + existingUri);
					try {
						geoPointToUpdate.setLongitude(longitude);
						geoPointToUpdate.setLatitude(latitude);
						geoPointToUpdate.setTitleInfo(title);
						geoPointToUpdate.setTextInfo(info);
						getEndpoint().updateGeoPointInfo(geoPointToUpdate)
								.execute();
					} catch (IOException e1) {
						Log.i(TAG, "Exception when update geoPoint");
						e1.printStackTrace();
					}
					syncResult.stats.numUpdates++;
				} else {
					Log.i(TAG, "No action: " + existingUri);
				}
			} else if (pointId == 0) {
				try {
					GeoPointInfo geoPointToInsert = new GeoPointInfo();
					geoPointToInsert.setLatitude(latitude);
					geoPointToInsert.setLongitude(longitude);
					geoPointToInsert.setTimestamp(dateOfInsert);
					geoPointToInsert.setTitleInfo(title);
					geoPointToInsert.setTextInfo(info);
					getEndpoint().insertGeoPointInfo(geoPointToInsert)
							.execute();
					Log.i(TAG, "Sheduling insert to datastore point latitude"
							+ latitude + ",longitude-" + longitude);
				} catch (IOException e1) {
					Log.i(TAG, "Exception when insert to datastore");
					e1.printStackTrace();
				}
				// delete
				Uri deleteUri = FeedContract.Entry.CONTENT_URI.buildUpon()
						.appendPath(Integer.toString(id)).build();
				Log.i(TAG, "Scheduling delete: " + deleteUri);
				batch.add(ContentProviderOperation.newDelete(deleteUri).build());
				syncResult.stats.numDeletes++;
			}
		}
		c.close();
		final List<GeoPointInfo> insertedRemoteEntries = getGeoPointsFromDataStore();
		if (insertedRemoteEntries != null)
			for (GeoPointInfo g : insertedRemoteEntries) {
				if (!nonInsertIDs.contains(g.getId())) {
					Log.i(TAG, "Scheduling insert: entry_id=" + g.getId());
					insertToBatch(batch,g);
					syncResult.stats.numInserts++;
				}
			}
		Log.i(TAG, "Merge solution ready. Applying batch update");
		try {
			mContentResolver.applyBatch(FeedContract.CONTENT_AUTHORITY, batch);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (OperationApplicationException e1) {
			e1.printStackTrace();
		}

		mContentResolver.notifyChange(FeedContract.Entry.CONTENT_URI, // URI
																		// where
																		// data
																		// was
																		// modified
				null, // No local observer
				false); // IMPORTANT: Do not sync to network
		// This sample doesn't support uploads, but if *your* code does, make
		// sure you set
		// syncToNetwork=false in the line above to prevent duplicate syncs.

		// Add new items

	}

	private void insertToBatch(ArrayList<ContentProviderOperation> batch,
			GeoPointInfo g) {
		batch.add(ContentProviderOperation
				.newInsert(FeedContract.Entry.CONTENT_URI)
				.withValue(FeedContract.Entry.COLUMN_NAME_POINT_ID,
						g.getId())
				.withValue(FeedContract.Entry.COLUMN_NAME_LATITUDE,
						g.getLatitude())
				.withValue(FeedContract.Entry.COLUMN_NAME_LONGITUDE,
						g.getLongitude())
				.withValue(
						FeedContract.Entry.COLUMN_NAME_DATE_OF_INSERT,
						g.getTimestamp())
				.withValue(FeedContract.Entry.COLUMN_NAME_TITLE,
						g.getTitleInfo())
				.withValue(FeedContract.Entry.COLUMN_NAME_INFO,
						g.getTextInfo())
				.withValue(FeedContract.Entry.COLUMN_NAME_ACCOUNT,
						account.name).build());
		
	}

	private GeoPointInfo getGeoPointFromDataStore(Long id) {
		try {
			return getEndpoint().getGeoPointInfo(id).execute();
		} catch (IOException e) {
			Log.i(TAG, "Error when getting point with id " + id);
			e.printStackTrace();
			return null;
		}
	}

	private List<GeoPointInfo> getGeoPointsFromDataStore() {
		CollectionResponseGeoPointInfo listOfPoints;
		try {
			listOfPoints = getEndpoint().listGeoPointInfo().execute();
		} catch (IOException e) {
			Log.i(TAG, "Exception when get all points from datastore");
			e.printStackTrace();
			return null;
		}
		return listOfPoints.getItems();
	}

	private Geopointinfoendpoint getEndpoint() {
		try {
			credential = StartActivity.getCredential();
		} catch (Exception ex) {
			credential = createAndGetCredential();
		}

		if (endpointBuilder == null) {
			endpointBuilder = new Geopointinfoendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					credential == null ? createAndGetCredential() : credential);
		}
		return geopointInfoEndpoint == null ? CloudEndpointUtils.updateBuilder(
				endpointBuilder).build() : geopointInfoEndpoint;
	}

	private GoogleAccountCredential createAndGetCredential() {
		SharedPreferences settings = getContext().getSharedPreferences(
				"Geodesic", 0);
		GoogleAccountCredential credential = GoogleAccountCredential
				.usingAudience(
						getContext(),
						"server:client_id:274430009138-4f8ufdnqj56u9mrclvamdfdah9kujcs1.apps.googleusercontent.com");
		credential.setSelectedAccountName(settings.getString("ACCOUNT_NAME",
				null));
		return credential;
	}
}
