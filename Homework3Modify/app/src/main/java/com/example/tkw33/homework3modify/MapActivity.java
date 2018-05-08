package com.example.tkw33.homework3modify;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tkw33.homework3modify.DB.MakeGraph;
import com.example.tkw33.homework3modify.GeoJson.LinkLatLng;
import com.example.tkw33.homework3modify.GeoJson.NodeLatLng;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
    boolean isGranted = false;
    public static Context mContext;
    ArrayList<LinkLatLng> jeju_link = new ArrayList<>();
    ArrayList<NodeLatLng> jeju_node = new ArrayList<>();
    boolean isLongTouched = false;
    MapPoint[] findNode = new MapPoint[2];
    //LinkedList<NodeLatLng> open = new LinkedList<>();
    //LinkedList<NodeLatLng> closed = new LinkedList<>();
    int sIndex = 0, eIndex = 0;
    NodeLatLng startNode;
    NodeLatLng endNode;
    int k = 0;
    ListStack listStack;

    //DBHelper dbHelper;
    //SQLiteDatabase db;
    //double timeAvg = 0.0;
    class ListStack {
        String node_id;
        double FScore, GScore, HScore;
        String parent;
    }
    /*class ClosedList {
        String node_id;
        double FScore, GScore, HScore;
        NodeLatLng parent;
    }*/
    //ClosedList[] closed = new ClosedList[jeju_node.size()];
    //OpenList[] open = new OpenList[jeju_node.size()];
    LinkedList<ListStack> open = new LinkedList<>();
    LinkedList<ListStack> closed = new LinkedList<>();
    //ListStack[] open = new ListStack[jeju_node.size()];
    //ListStack[] closed = new ListStack[jeju_node.size()];
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
                if(Build.VERSION.SDK_INT >= 23) {
                    PermissionCheck permissionCheck = new PermissionCheck(MapActivity.this,
                            "location");
                    permissionCheck.requestPermission();
                }
                //if (permissionCheck.isPermissionGranted == true) {
                if (isGranted != false) {
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

        //jeju_node = NodeLatLng.nlist;
        //Toast.makeText(mContext, ""+NodeLatLng.nlist.get(0).node_id+""+NodeLatLng.nlist.get(0).getLink().get(0).distance, Toast.LENGTH_SHORT).show();
        geoCoding();
        createMarker(mapView);
        container.addView(mapView);
        mContext = this;
    }

    public class SearchRoute extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            startFindRoute();
            startNode = jeju_node.get(sIndex);
            ListStack listStack = new ListStack();
            listStack.node_id = startNode.node_id;
            listStack.GScore = 0;
            listStack.HScore = 0;
            listStack.FScore = listStack.GScore + listStack.HScore;
            listStack.parent = startNode.node_id;
            closed.add(listStack);
            AstarAlgorithm(sIndex, listStack.GScore);
            /*
            MapPolyline mapPolyline = new MapPolyline();
            mapPolyline.setLineColor(Color.argb(128, 0, 255, 255));

            mapPolyline.addPoint(MapPoint.mapPointWithGeoCoord();
            mapPolyline.addPoint(MapPoint.mapPointWithGeoCoord();
            mapView.addPolyline(mapPolyline);
            */
            return null;
        }
    }
    public void startFindRoute(){   //asynctask에서 돌리기
        MakeGraph makeGraph = new MakeGraph(
                findNode[0].getMapPointGeoCoord().latitude, findNode[0].getMapPointGeoCoord().longitude,
                findNode[1].getMapPointGeoCoord().latitude, findNode[1].getMapPointGeoCoord().longitude);
        //jeju_link = makeGraph.init();
        jeju_node = makeGraph.init();
        //getDistance(jeju_link);
        int i = 0, j = 0;
        float smin = 0, emin = 0;
        //float weight = 0;


        float[] minSPointWeight = new float[jeju_node.size()];
        float[] minEPointWeight = new float[jeju_node.size()];
        for(i = 0; i < jeju_node.size(); i++) {
            Location locationSP = new Location("point A");
            locationSP.setLatitude(findNode[0].getMapPointGeoCoord().latitude);
            locationSP.setLongitude(findNode[0].getMapPointGeoCoord().longitude);

            Location locationEP = new Location("point B");
            locationEP.setLatitude(findNode[1].getMapPointGeoCoord().latitude);
            locationEP.setLatitude(findNode[1].getMapPointGeoCoord().longitude);

            Location locationN = new Location("point C");
            locationN.setLatitude(jeju_node.get(i).lat);
            locationN.setLongitude(jeju_node.get(i).lng);


            minSPointWeight[i] = locationSP.distanceTo(locationN);
            minEPointWeight[i] = locationEP.distanceTo(locationN);
        }
        smin = minSPointWeight[0];
        emin = minEPointWeight[0];
        for(j = 1; j < i; j++){
            if(smin>minSPointWeight[j]) {
                smin = minSPointWeight[j];
                sIndex = j;
            }
            if(emin>minEPointWeight[j]) {
                emin = minEPointWeight[j];
                eIndex = j;
            }
        }

        MapPolyline polyline = new MapPolyline();
        MapPolyline polyline2 = new MapPolyline();
        polyline.setLineColor(Color.argb(128, 255, 255, 0));
        polyline2.setLineColor(Color.argb(128, 255, 255, 0));

        polyline.addPoint(MapPoint.mapPointWithGeoCoord(findNode[0].getMapPointGeoCoord().latitude, findNode[0].getMapPointGeoCoord().longitude));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(jeju_node.get(sIndex).lat, jeju_node.get(sIndex).lng));
        mapView.addPolyline(polyline);

        polyline2.addPoint(MapPoint.mapPointWithGeoCoord(findNode[1].getMapPointGeoCoord().latitude, findNode[1].getMapPointGeoCoord().longitude));
        polyline2.addPoint(MapPoint.mapPointWithGeoCoord(jeju_node.get(eIndex).lat, jeju_node.get(eIndex).lng));
        mapView.addPolyline(polyline2);

        //MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        //int padding = 10;
        //mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }
    public void AstarAlgorithm(int sIndex, double g_Score) {
        NodeLatLng startNode = jeju_node.get(sIndex);
        NodeLatLng endNode;
        String[] tmp = new String[startNode.getLink().size() - 1];
        double min;
        int index = 0;

        for(int len = 0; len < tmp.length; len++){  //startNode에 연결된 링크만 임시로 저장
            //만약 startNode.getLink().get(len).getNode().node_id가 closed배열에 있으면 continue;
            if(closed.contains(startNode.getLink().get(len).getNextNode().node_id))   //getLink.get(i%2)로
                continue;
            tmp[index++] = startNode.getLink().get(len).getNextNode().node_id;
        }

        //startNode.getLink().get(i).getNode().node_id;
        for(int i = 0; i < jeju_node.size(); i++) {
            if(jeju_node.get(i).node_id == jeju_node.get(eIndex).node_id) //노드id로 비교
                break;
            for(int j = 0; j < tmp.length; j++) {
                if (jeju_node.get(i).node_id == tmp[j]) {
                    startNode = jeju_node.get(sIndex);
                    endNode = jeju_node.get(i);
                    Location locationS = new Location("point A");
                    Location locationE = new Location("point B");
                    locationS.setLatitude(startNode.lat);
                    locationS.setLongitude(startNode.lng);
                    locationE.setLatitude(endNode.lat);
                    locationE.setLongitude(endNode.lng);

                    listStack = new ListStack();
                    listStack.node_id = endNode.node_id;
                    listStack.GScore = g_Score + startNode.getLink().get(j).distance; //시작점부터 해당 노드까지의 거리 값. 시작노드부터의 거리로 수정해야함.
                    listStack.HScore = locationS.distanceTo(locationE);
                    listStack.FScore = listStack.GScore + listStack.HScore;

                    listStack.parent = jeju_node.get(sIndex).node_id;//.getLink().get(i).getNode().node_id;
                    //closed[i] = listStack;
                    //push로 데이터를 넣기 전에 스택에서 같은 노드의 값이 있다면 G값 비교해서 작은것으로 바꿔줌.
                    //open[k++] = listStack;  //링크드리스트 생성해서 push로 저장.

                    if(open.contains(listStack.node_id)) {
                        for (int k = 0; k < open.size(); k++) {
                            if (open.get(k).node_id == listStack.node_id) {
                                if (open.get(k).GScore > listStack.GScore) {
                                    open.remove(k);
                                    open.add(k, listStack);
                                }
                            }
                        }
                    }
                    else {
                        open.push(listStack);
                    }
                }
            }
        }
        int minOpenIndex = 0;
        min = open.get(0).FScore;
        for(int length = 1; length < open.size(); length++){
            if( min > open.get(length).FScore){
                min = open.get(length).FScore;
                minOpenIndex = length;

            }
        }
        for(int s = 0; s<jeju_node.size(); s++) {
            if(open.get(minOpenIndex).node_id == jeju_node.get(s).node_id)
                sIndex = s;
        }
        closed.push(open.get(minOpenIndex));
        open.remove(minOpenIndex);
        //LinkedList<String> l = new LinkedList<>();
        // open배열에 있는 F값중 가장 작은 인덱스를 closed배열에 저장. (open배열 해당 인덱스get해서 closed에 push 후 해당 인덱스remove)
        // closed[0]있으므로 인덱스 1부터 시작.
        //다음 호출할 때의 sIndex는 closed에 저장된 노드의 인덱스로. (closed 마지막 인덱스)
        if(open.get(minOpenIndex).node_id != jeju_node.get(eIndex).node_id)
            AstarAlgorithm(sIndex , listStack.GScore);
        //이부분 디버깅해보기 재귀호출하면서 오류터짐.//
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

        if(isLongTouched == false) {
            mapView.removeAllPOIItems();
            MapPOIItem sp_marker = new MapPOIItem();
            sp_marker.setItemName("출발지");
            sp_marker.setMapPoint(mapPoint);
            sp_marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            sp_marker.setCustomImageResourceId(R.drawable.spin);
            isLongTouched = true;
            mapView.addPOIItem(sp_marker);
            findNode[0] = mapPoint;
        }
        else {
            MapPOIItem ep_marker = new MapPOIItem();
            ep_marker.setItemName("도착지");
            ep_marker.setMapPoint(mapPoint);
            ep_marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            ep_marker.setCustomImageResourceId(R.drawable.epin);
            isLongTouched = false;
            mapView.addPOIItem(ep_marker);
            findNode[1] = mapPoint;
            //startFindRoute();
            SearchRoute searchRoute = new SearchRoute();
            searchRoute.execute();
        }
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
                int padding = 100;
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
                    isGranted = true;
                    bt_current_loaction.setEnabled(true);
                } else {
                    Log.d("onRequestResult denied", "result denied");
                    //Toast.makeText(this, "result denied", Toast.LENGTH_SHORT).show();
                    isGranted = false;
                    bt_current_loaction.setEnabled(false);
                }
                return;
            }
        }
    }
}
