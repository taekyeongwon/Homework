package com.example.tkw33.kakaomaptest;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class MainActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListAdapter listAdapter = new ListItemAdapter(this, ListItemData.listItemData);
        setListAdapter(listAdapter);

        /*CheckPermission checkPermission = new CheckPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(Build.VERSION.SDK_INT >= 23) {
            checkPermission.Checking();
        }*/
        /*MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("f4e3a228a169ced1063495a42711d190");
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1 : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("onRequestResult granted", "result granted");
                    Toast.makeText(this, "result granted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("onRequestResult denied", "result denied");
                    Toast.makeText(this, "result denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListItemData data = (ListItemData) getListAdapter().getItem(position);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("latData", data.lat);
        intent.putExtra("lonData", data.lon);
        intent.putExtra("purposeData", data.purpose);
        startActivity(intent);
    }
}
