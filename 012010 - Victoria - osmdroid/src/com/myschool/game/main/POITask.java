package com.myschool.game.main;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.bonuspack.location.FlickrPOIProvider;
import org.osmdroid.bonuspack.location.GeoNamesPOIProvider;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.location.PicasaPOIProvider;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.myschool.game.R;

     class POITask extends AsyncTask<Object, Void, List<POI>> {
                String mTag;
				private Context mContext;
				private MapView mMapView;
				private ProgressDialog mWaitSpinner;
                
                public POITask(Context mContext, MapView mMapView) {
                	this.mContext = mContext;
	                	this.mMapView = mMapView;
                    mWaitSpinner = new ProgressDialog(this.mContext);
                }

                @Override
                protected List<POI> doInBackground(Object... params) {
                        mTag = (String) params[0];

                        if (mTag == null || mTag.equals("")) {
                                return null;
                        }
                        if (mMapView.getZoomLevel() > 2 ) {
                            return null;
                        }
                        BoundingBoxE6 bb = mMapView.getBoundingBox();
                        Log.d("Alain", this.toString() + " doInBackground lati=" + bb.getLatitudeSpanE6());
                        Log.d("Alain", this.toString() + " doInBackground long=" + bb.getLongitudeSpanE6());


                       if (mTag.equals("wikipedia")) {
                                GeoNamesPOIProvider poiProvider = new GeoNamesPOIProvider("mkergall");
                                //ArrayList<POI> pois = poiProvider.getPOICloseTo(point, 30, 20.0);
                                //Get POI inside the bounding box of the current map view:
                                ArrayList<POI> pois = poiProvider.getPOIInside(bb, 50);
                                return pois;
                        } else if (mTag.equals("flickr")) {
                                FlickrPOIProvider poiProvider = new FlickrPOIProvider(
                                                "c39be46304a6c6efda8bc066c185cd7e");
                                ArrayList<POI> pois = poiProvider.getPOIInside(bb, 50);
                                return pois;
                        } else if (mTag.startsWith("picasa")) {
                                PicasaPOIProvider poiProvider = new PicasaPOIProvider(null);
                                String q = mTag.substring("picasa".length());
                                List<POI> pois = poiProvider.getPOIInside(bb, 50, q);
                                return pois;
                        }
                        /*else if (mTag.startsWith("foursquare")) {
                                FourSquareProvider poiProvider = new FourSquareProvider(null, null);
                                BoundingBoxE6 bb = mMapView.getBoundingBox();
                                String q = mTag.substring("foursquare".length());
                                //                                String q = mTag.substring("picasa".length());
                                ArrayList<POI> pois = poiProvider.getPOIInside(bb, q, 40);
                                return pois;
                        }*/
                        else {
                                NominatimPOIProvider poiProvider = new NominatimPOIProvider();
                                //        poiProvider.setService(NominatimPOIProvider.MAPQUEST_POI_SERVICE);
                                poiProvider.setService(NominatimPOIProvider.NOMINATIM_POI_SERVICE);
                                ArrayList<POI> pois;
                                //                                if (destinationPoint == null) {
                                pois = poiProvider.getPOIInside(bb, mTag, 50);

                                //pois = poiProvider.getPOI( mTag, 10);
                                //        } else {
                                //                pois = poiProvider.getPOIAlong(mRoad.getRouteLow(), mTag, 100, 2.0);
                                //        }
                                return pois;
                        }
                }

                @Override
                protected void onPostExecute(List<POI> pois) {
                    	mWaitSpinner.cancel();
                        if (mTag.equals("")) {
                                //no search, no message
                        } else if (pois == null) {
                                Toast
                                                .makeText(mContext.getApplicationContext(),
                                                                "Technical issue when getting " + mTag + " POI.", Toast.LENGTH_LONG)
                                                .show();
                        } else {
                                Toast.makeText(mContext.getApplicationContext(),
                                                "" + pois.size() + " " + mTag + " entries found",
                                                Toast.LENGTH_LONG).show();
                                //        if (mTag.equals("flickr") || mTag.startsWith("picasa") || mTag.equals("wikipedia"))
                                //        startAsyncThumbnailsLoading(mPOIs);
                        }
                       //super.onPostExecute(pois);
                       
               		for (POI poi:pois){
               			Log.d("Alain ", "item locationE6 lat=" + poi.mLocation.getLatitudeE6() + " long=" + poi.mLocation.getLongitudeE6());
                        ExtendedOverlayItem poiItem = new ExtendedOverlayItem(
                                                poi.mType, poi.mDescription, 
                                                poi.mLocation, mMapView.getContext());
                        Drawable poiMarker = mContext.getResources().getDrawable(R.drawable.pin_default);
                        poiItem.setMarker(poiMarker);
                        poiItem.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
                        if (poi.mThumbnail != null){
                                poiItem.setImage(new BitmapDrawable(poi.mThumbnail));
                        }
                		final ArrayList<ExtendedOverlayItem> poiItems = new ArrayList<ExtendedOverlayItem>();
                		ItemizedOverlayWithBubble<ExtendedOverlayItem> poiMarkers = new ItemizedOverlayWithBubble<ExtendedOverlayItem>(
                				mContext, poiItems, mMapView, new CustomMapInfoWindow(mMapView));
                		mMapView.getOverlays().add(poiMarkers);
                        poiMarkers.addItem(poiItem);
               		}
                }

     }
                
        