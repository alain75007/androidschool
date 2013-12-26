package com.myschool.game.main;

import org.osmdroid.ResourceProxy.string;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;

public class OpenStreetMapSource extends OnlineTileSourceBase {


	public OpenStreetMapSource(String aName, string aResourceId,
			int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels,
			String aImageFilenameEnding, String[] aBaseUrl) {
		super(aName, aResourceId, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels,
				aImageFilenameEnding, aBaseUrl);
		// TODO Auto-generated constructor stub
	}

	private static final String[] gMapsBaseURLs = { "http://tile.openstreetmap.org" };

	public void OpenStreetMap(int displayZoom) {
		//(displayZoom);
		//super("OSM", null, 3, 18, 256 * displayZoom, "", gMapsBaseURLs);
	}

	@Override
	public String getTileURLString(MapTile arg0) {
		String url = getBaseUrl() + "/" + arg0.getZoomLevel() + "/"
				+ arg0.getX() + "/" + arg0.getY() + ".png";
		return url;
	}
}
