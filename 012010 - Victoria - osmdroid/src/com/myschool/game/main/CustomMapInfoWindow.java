package com.myschool.game.main;

import org.osmdroid.views.MapView;

import org.osmdroid.bonuspack.overlays.DefaultInfoWindow;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;

import com.myschool.game.R;


public class CustomMapInfoWindow extends DefaultInfoWindow {

    public CustomMapInfoWindow(MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);

    }

/*    @Override
    public void onOpen(ExtendedOverlayItem item) {
        super.onOpen(item);
    } */
}
