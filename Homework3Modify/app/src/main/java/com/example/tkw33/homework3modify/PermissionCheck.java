package com.example.tkw33.homework3modify;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import net.daum.android.map.MapActivity;

public class PermissionCheck extends AppCompatActivity {
    private final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private Activity context;
    private String permission;
    public PermissionCheck(Activity context, String permission){
        this.context = context;
        this.permission = permission;
    }

    public void requestPermission(){
        if(ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                //Toast.makeText(context, "permission denied", Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(context, "don't show message again", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}
