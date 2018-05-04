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
    private final static int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;

    private Activity context;
    private String permission;
    private String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public PermissionCheck(Activity context, String permission){
        this.context = context;
        this.permission = permission;
    }

    public void requestPermission(){
        if(permission == "location") {
            if (ContextCompat.checkSelfPermission(context, permissions[0])
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[0])) {
                    //Toast.makeText(context, "permission denied", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "don't show message again", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(context, new String[]{permissions[0]},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        if(permission == "storage") {
            if (ContextCompat.checkSelfPermission(context, permissions[1])
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, permissions[2])
                            != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[1])
                        && ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[2])) {

                } else {

                }
                ActivityCompat.requestPermissions(context, new String[] {permissions[1], permissions[2]},
                        MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            }
        }
    }
}
