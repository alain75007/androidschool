package com.myschool.game.main;

import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.modules.MapTileDownloader;
import org.osmdroid.tileprovider.modules.MapTileFilesystemProvider;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck;
import org.osmdroid.tileprovider.modules.TileWriter;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myschool.game.R;
import com.myschool.game.shopping.ShopChooserFragment;

public class GameActivity extends FragmentActivity {

	private static final String PREFS_APP_CENTRE_LON = "current longitude";
	private static final String PREFS_APP_CENTRE_LAT = "current latitude";
	private static final String PREFS_APP_ZOOM_LEVEL = "current zoom level";
	private static final String PREFS_APP_MY_LOCATION = "current location";
	private static final String PREFS_APP_FOLLOW_LOCATION = "follow location";
	private static final String PREFS_APP_PROVIDER = "location provider";
	private static final long TIME_TO_WAIT_IN_MS = 100;
	private MyApplication mMyApplication;
	private MapView mMapView;
	private ArrayList<OverlayItem> mItems;
	private Location mCurrentLocation;
	private ResourceProxy mResourceProxy;
	private GameActivity mContext;
	private GeoPoint mCurrentPoint;
	private MapController mMapController;
	private IGeoPoint mSaveMapCenter;
	private int mSaveMapZoomLevel;
	private SharedPreferences mPrefs;
	private int mCurrentZoom;
	private MyLocationNewOverlay mMyLocationOverlay;
	private ItemizedIconOverlay<OverlayItem> mMyItimizedLocationOverlay;
	private String myLocationProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		mContext = this;
		mMyApplication = (MyApplication) getApplicationContext();

		((TextView) findViewById(R.id.act_game_text_charname))
				.setText(mMyApplication.getCharNameAndType());

		//DatabaseHelper databaseHelper = new DatabaseHelper(mMyApplication);
		//databaseHelper.initializeDatabase();
	    mPrefs = this.getSharedPreferences("com.myschool.game", Context.MODE_PRIVATE);
		createMapView();
		Button button= (Button) findViewById(R.id.act_game_btn_return_to_current_location);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				Log.d("Alain", "returnToCurrentPosition");
				gotoCurrentLocation();
		    }
		});	
	}


	public void onShopChooserButtonClick(View v) {
		DialogFragment dialog = new ShopChooserFragment();
		dialog.show(this.getSupportFragmentManager(), "NoticeDialogFragment");
	}


	@Override
	protected void onPause() {
	    SharedPreferences.Editor edit = mPrefs.edit();

		mSaveMapCenter = mMapView.getMapCenter();
	    float longitude = mSaveMapCenter.getLongitudeE6() *1E-6f;
	    float latitude = mSaveMapCenter.getLatitudeE6() *1E-6f;
	    edit.putFloat(PREFS_APP_CENTRE_LON, longitude);
	    edit.putFloat(PREFS_APP_CENTRE_LAT, latitude);
	    
		mSaveMapZoomLevel = mMapView.getZoomLevel();
	    edit.putInt(PREFS_APP_ZOOM_LEVEL,mSaveMapZoomLevel);
	    
		MapTileProviderBase tileProvider = mMapView.getTileProvider();
		String tileProviderName = tileProvider.getTileSource().name();
		edit.putString(PREFS_APP_PROVIDER, tileProviderName);


	    //edit.putBoolean(PREFS_APP_MY_LOCATION, mCurrentLocation.isMyLocationEnabled());
	    //edit.putBoolean(PREFS_APP_FOLLOW_LOCATION, mCurrentLocation.isFollowLocationEnabled());
	    //disableMyLocation();
	    //controllerOverlay_.onPause(edit);
	    edit.commit();
		Log.d("Alain", "Save location=" + latitude + "," + longitude + " Zoom=" + mCurrentZoom);
		
		super.onPause();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	

	private void createMapView() {
		Log.d("Alain", "coucou");
		mMapView = (MapView) findViewById(R.id.mapview);
		// mMapView.setTileSource(TileSourceFactory.MAPNIK);
		final ITileSource tileSource = new XYTileSource("Mapnik",
				ResourceProxy.string.mapnik, 0, 18, 258, ".png",
				"http://tile.openstreetmap.org/");
		mMapView.setTileSource(tileSource);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setMultiTouchControls(true);
		mMapController = (MapController) mMapView.getController();

		reinitLocation();
		mMapView.postDelayed(waitForMapTimeTask, TIME_TO_WAIT_IN_MS);

		addPoiOverlay();
	}
	
	 @Override
	protected void onResume() {
		Log.d("Alain", "onResume");
		mMapView.postDelayed(waitForMapTimeTask, TIME_TO_WAIT_IN_MS);
		super.onResume();
	}

	/**
	 * Wait for mapview to become ready.
	 * 
	 * getLatitudeSpan and getLongitudeSpan only works to give you
	 * the spans for the visible portion of the map. So if you call them in
	 * onCreate() the map will not always have been displayed yet.
	 * 
	 */
	private Runnable waitForMapTimeTask = new Runnable() {
		public void run() {
			// If either is true we must wait.
			Log.d("Alain", " xxx longiture=" + mMapView.getLongitudeSpan());
			Log.d("Alain", " xxx lattitude=" + mMapView.getLatitudeSpan());
			Log.d("Alain", " xxx zoom=" + mMapView.getZoomLevel());

			if (mMapView.getLatitudeSpan() == 0
					|| mMapView.getLongitudeSpan() == 360000000)
				mMapView.postDelayed(waitForMapTimeTask, TIME_TO_WAIT_IN_MS);
		}
	};
   
   
	private void reinitLocation() {
		getRealLocation();
	    addIcons();

        mCurrentZoom = mPrefs.getInt(PREFS_APP_ZOOM_LEVEL, 18);
		mMapController.setZoom(mCurrentZoom);
		
		double latitude  = mPrefs.getFloat(PREFS_APP_CENTRE_LAT,  (float) mCurrentLocation.getLatitude());
		double longitude =  mPrefs.getFloat(PREFS_APP_CENTRE_LON, (float)  mCurrentLocation.getLongitude());
		mCurrentPoint = new GeoPoint(latitude, longitude);
        mMapView.getController().animateTo(mCurrentPoint);
        displayMyIcon();
        
		Log.d("Alain", "Restored location=" + latitude + "," + longitude + " Zoom=" + mCurrentZoom);

	}
	
	private void gotoCurrentLocation() {
		getRealLocation();
	    addIcons();
	    //mMapController.setCenter(mCurrentPoint);
	    mCurrentZoom = 18;
		mMapController.setZoom(mCurrentZoom);
        mMapView.getController().animateTo(mCurrentPoint);
		Log.d("Alain", "Restore default location and Zoom");
		addPoiOverlay();
		
	}
	
	private void getRealLocation() {
		String status = android.provider.Settings.Secure.getString(getContentResolver(), Secure.LOCATION_PROVIDERS_ALLOWED);
		//Toast.makeText(this, status, Toast.LENGTH_LONG).show();
		Log.d("Alain", status);
		
		/**
		 * table vuser  in redis
		 * @charuuid : charname ++ uuid (see http://developer.android.com/reference/java/util/UUID.html)
		 * @email (optional)
		 * @real_longitude
		 * @real_latitude 
		 * @virtual_latitude
		 * @virtual_longitude
		 * @date timestamp
		 * 
		 * 
		 */

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // TODO never updated : use this http://android-developers.blogspot.fr/2011/06/deep-dive-into-location.html
        if( mCurrentLocation != null ) {
            myLocationProvider = LocationManager.GPS_PROVIDER;
            mCurrentPoint = new GeoPoint(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
    		Log.d("Alain", "location from GPS " + mCurrentPoint.toDoubleString());
        }
        else {
            mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if( mCurrentLocation != null ) {
                myLocationProvider = LocationManager.NETWORK_PROVIDER;
                mCurrentPoint = new GeoPoint(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        		Log.d("Alain", "location from network " + mCurrentPoint.toDoubleString());
            }
            else  {
                myLocationProvider = LocationManager.NETWORK_PROVIDER;
            	//GeoPoint gPt = new GeoPoint(51500000, -150000);
            	mCurrentPoint = new GeoPoint(49050970, 2100640); 
            	mCurrentLocation = new Location("me");
            	mCurrentLocation.setLatitude(mCurrentPoint.getLatitude());
            	mCurrentLocation.setLongitude(mCurrentPoint.getLongitude());
        		Log.d("Alain", "No location provide found " + mCurrentPoint.toDoubleString());
            }
        }
	}
	
	private void addIcons() {
		mItems = new ArrayList<OverlayItem>();
		OverlayItem olItem = new OverlayItem("Here", "SampleDescription", mCurrentPoint);
		Drawable newMarker = this.getResources().getDrawable(R.drawable.ic_launcher);
		olItem.setMarker(newMarker);
		mItems.add(olItem);
		mResourceProxy = new DefaultResourceProxyImpl(this);
		//mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(mItems, newMarker, new Glistener(), mResourceProxy);
		mMyItimizedLocationOverlay = new ItemizedIconOverlay<OverlayItem>(mItems, newMarker, null, mResourceProxy );
        mMapView.getOverlays().clear();
        mMapView.getOverlays().add(mMyItimizedLocationOverlay);

	}
	
	private void displayMyIcon() {
		GpsMyLocationProvider imlp = new GpsMyLocationProvider(this.getBaseContext());
		imlp.setLocationUpdateMinDistance(1000);
		imlp.setLocationUpdateMinTime(60000);

		mMyLocationOverlay = new MyLocationNewOverlay(this.getBaseContext(), imlp, mMapView);
		mMyLocationOverlay.setUseSafeCanvas(false);
		mMyLocationOverlay.setDrawAccuracyEnabled(true);
		mMapView.getOverlays().add(mMyLocationOverlay);

		
		mMyLocationOverlay = new MyLocationNewOverlay(mContext,  mMapView);
        mMapView.getOverlays().add(this.mMyLocationOverlay);
        mMyLocationOverlay.enableMyLocation();
	}
	
	public void addPoiOverlay() {
		String mTag = "wikipedia";
		new POITask(this, mMapView).execute(mTag);
/*		final ArrayList<ExtendedOverlayItem> poiItems = new ArrayList<ExtendedOverlayItem>();
		ItemizedOverlayWithBubble<ExtendedOverlayItem> poiMarkers = new ItemizedOverlayWithBubble<ExtendedOverlayItem>(
				this, poiItems, mMapView, new CustomMapInfoWindow(mMapView));
		mMapView.getOverlays().add(poiMarkers);
		NominatimPOIProvider poiProvider = new NominatimPOIProvider();
		// poiProvider.setService(NominatimPOIProvider.MAPQUEST_POI_SERVICE);
		poiProvider.setService(NominatimPOIProvider.NOMINATIM_POI_SERVICE);

		BoundingBoxE6 bb = mMapView.getBoundingBox();
		ArrayList<POI> pois = poiProvider.getPOIInside(bb, mTag, 10);
		pois = poiProvider.getPOIInside(bb, mTag, 10);

		// GeoPoint igp = (GeoPoint) mMapView.getMapCenter();
		// ArrayList<POI> pois = poiProvider.getPOICloseTo( igp, "cinema", 50,
		// 0.1);

		for (POI poi : pois) {
			ExtendedOverlayItem poiItem = new ExtendedOverlayItem(poi.mType,
					poi.mDescription, poi.mLocation, mMapView.getContext());
			Drawable poiMarker = getResources().getDrawable(
					R.drawable.pin_default);
			poiItem.setMarker(poiMarker);
			poiItem.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
			if (poi.mThumbnail != null) {
				poiItem.setImage(new BitmapDrawable(poi.mThumbnail));
			}
			poiMarkers.addItem(poiItem);
		}*/
	}
	
	private void createMapOBSO() {
		final Context context = this;
		final Context applicationContext = context.getApplicationContext();
		final IRegisterReceiver registerReceiver = new SimpleRegisterReceiver(applicationContext);

		// Create a custom tile source
		final ITileSource tileSource = new XYTileSource("Mapnik", ResourceProxy.string.mapnik, 1, 18, 256, ".png", "http://tile.openstreetmap.org/");

		// Create a file cache modular provider
		final TileWriter tileWriter = new TileWriter();
		final MapTileFilesystemProvider fileSystemProvider = new MapTileFilesystemProvider(registerReceiver, tileSource);

		// Create an archive file modular tile provider
		//GEMFFileArchive gemfFileArchive = GEMFFileArchive.getGEMFFileArchive(mGemfArchiveFilename ); // Requires try/catch
		//MapTileFileArchiveProvider fileArchiveProvider = new MapTileFileArchiveProvider(registerReceiver, tileSource, new IArchiveFile[] { gemfFileArchive });

		// Create a download modular tile provider
		final NetworkAvailabliltyCheck networkAvailabliltyCheck = new NetworkAvailabliltyCheck(context);
		final MapTileDownloader downloaderProvider = new MapTileDownloader(tileSource, tileWriter, networkAvailabliltyCheck);

		// Create a custom tile provider array with the custom tile source and the custom tile providers
		//final MapTileProviderArray tileProviderArray = new MapTileProviderArray(tileSource, registerReceiver, new MapTileModuleProviderBase[] { fileSystemProvider, fileArchiveProvider, downloaderProvider });
		final MapTileProviderArray tileProviderArray = new MapTileProviderArray(tileSource, registerReceiver, new MapTileModuleProviderBase[] { fileSystemProvider, downloaderProvider });
		//final MapTileProviderArray tileProviderArray = new MapTileProviderArray(tileSource, registerReceiver, new MapTileModuleProviderBase[] { downloaderProvider });

		// Create the mapview with the custom tile provider array
		//mMapView = new MapView(context, 256, new DefaultResourceProxyImpl(context), tileProviderArray);
        MapView mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(tileSource);        

	}
}
