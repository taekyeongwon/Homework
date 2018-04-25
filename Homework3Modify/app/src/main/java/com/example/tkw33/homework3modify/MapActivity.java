package com.example.tkw33.homework3modify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener,
        MapView.POIItemEventListener, MapView.CurrentLocationEventListener{

    MapView mapView;
    MapPoint geoCoordinate;
    JsonItemData mData;
    ImageButton bt_current_loaction;
    RelativeLayout container;
    List<Address> sp_list = null, ep_list = null;
    Address sp_address = null, ep_address = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        bt_current_loaction = (ImageButton) findViewById(R.id.bt_current_location);
        container = (RelativeLayout) findViewById(R.id.map_view);
        Bundle bundle = getIntent().getExtras();
        mData = bundle.getParcelable("JsonData");

        bt_current_loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionCheck permissionCheck = new PermissionCheck(MapActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if(Build.VERSION.SDK_INT >= 23) {
                    permissionCheck.requestPermission();
                }
                if (permissionCheck.isPermissionGranted == true) {
                    mapView.setCurrentLocationTrackingMode(MapView.
                            CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                }
            }
        });
        geoCoordinate = MapPoint.mapPointWithGeoCoord(mData.latitude, mData.longitude);
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(GetHashKey.AppKey);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        geoCoding();
        createMarker(mapView);
        container.addView(mapView);
    }
    public void geoCoding(){
        final Geocoder geocoder = new Geocoder(this);

        try{
            sp_list = geocoder.getFromLocationName(mData.startPoint, 10);
            ep_list = geocoder.getFromLocationName(mData.endPoint, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createMarker(MapView mapView){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(mData.roadName);
        marker.setMapPoint(geoCoordinate);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
        if(sp_list.size() > 0 && ep_list.size() > 0){
            sp_address = sp_list.get(0);
            ep_address = ep_list.get(0);

            MapPOIItem sp_marker = new MapPOIItem();
            sp_marker.setItemName(mData.startPoint);
            sp_marker.setMapPoint(MapPoint.mapPointWithGeoCoord(sp_address.getLatitude(), sp_address.getLongitude()));
            sp_marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            sp_marker.setCustomImageResourceId(R.drawable.spin);

            MapPOIItem ep_marker = new MapPOIItem();
            ep_marker.setItemName(mData.endPoint);
            ep_marker.setMapPoint(MapPoint.mapPointWithGeoCoord(ep_address.getLatitude(), ep_address.getLongitude()));
            ep_marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            ep_marker.setCustomImageResourceId(R.drawable.epin);

            mapView.addPOIItem(sp_marker);
            mapView.addPOIItem(ep_marker);
        }
        else {
            Toast.makeText(this, "주소정보를 좌표로 변환할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setMapCenterPointAndZoomLevel(geoCoordinate, -2, true);
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
        MapPolyline polyline = new MapPolyline();
        polyline.setLineColor(Color.argb(128, 255, 255, 0));

        if(sp_address != null && ep_address != null) {
            if (!mData.startPoint.equals(mData.endPoint)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(sp_address.getLatitude(), sp_address.getLongitude()));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(ep_address.getLatitude(), ep_address.getLongitude()));
                mapView.addPolyline(polyline);
                MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
                int padding = 10;
                mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
            } else {
                Toast.makeText(this, "기점과 종점의 주소가 같습니다.", Toast.LENGTH_SHORT).show();
            }
        }
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
