package com.example.tkw33.homework3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener{
    int index;
    MapView mapView;
    MapPoint geoCoordinate;
    JsonItemData[] mData;
    ImageButton bt_current_loaction;
    RelativeLayout container;
    static boolean permission = true;
    //MapPoint mapPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        bt_current_loaction = (ImageButton) findViewById(R.id.bt_current_location);
        container = (RelativeLayout) findViewById(R.id.map_view);
        Intent intent = getIntent();
        index = intent.getIntExtra("Index",0);
        mData = JsonItemData.jsonDataArray;

        bt_current_loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionCheck permissionCheck = new PermissionCheck(MapActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if(Build.VERSION.SDK_INT >= 23) {
                    permissionCheck.requestPermission();
                }
                if (permission == true) {
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

                }
            }
        });
        geoCoordinate = MapPoint.mapPointWithGeoCoord(mData[index].getLatitude(), mData[index].getLongitude());
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(GetHashKey.AppKey);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        createMarker(mapView);
        container.addView(mapView);
    }
    public void createMarker(MapView mapView){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(mData[index].purpose);
        marker.setMapPoint(geoCoordinate);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
        //mapView.setMapCenterPointAndZoomLevel(geoCoordinate, 9, true);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setMapCenterPointAndZoomLevel(geoCoordinate, 9, true);
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

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        //mapView.selectPOIItem(mapPOIItem, true);
        Log.d("onPOIItemSelected", "touched");
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d("onCalloutBalloon", "POIItem Touched");
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 4, true);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1 : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("onRequestResult granted", "result granted");
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                    //Toast.makeText(this, "result granted", Toast.LENGTH_SHORT).show();
                    bt_current_loaction.setEnabled(true);
                } else {
                    Log.d("onRequestResult denied", "result denied");
                    //Toast.makeText(this, "result denied", Toast.LENGTH_SHORT).show();
                    bt_current_loaction.setEnabled(false);
                }
                return;
            }
        }
    }
}
