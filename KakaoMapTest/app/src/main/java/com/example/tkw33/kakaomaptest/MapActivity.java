package com.example.tkw33.kakaomaptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener{
    private String lat, lon, purpose;
    private MapPoint geoCoordinate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("f4e3a228a169ced1063495a42711d190");
        mapView.setMapViewEventListener(this);
        Intent intent = getIntent();
        lat = intent.getStringExtra("latData");
        lon = intent.getStringExtra("lonData");
        purpose = intent.getStringExtra("purposeData");
        geoCoordinate = MapPoint.mapPointWithGeoCoord(Double.parseDouble(lat), Double.parseDouble(lon));
        createMarker(mapView);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);
    }

    public void createMarker(MapView mapView){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(purpose);
        marker.setTag(0);
        marker.setMapPoint(geoCoordinate);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);
        mapView.setMapCenterPointAndZoomLevel(geoCoordinate, 9, true);
    }
    @Override
    public void onMapViewInitialized(MapView mapView) {
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
