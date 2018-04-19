package com.example.tkw33.kakaomaptest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class CheckPermission extends AppCompatActivity {
    public final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private Activity activity;
    private String permission;
    public CheckPermission(Activity activity, String permission){
        this.activity = activity;
        this.permission = permission;
    }

    public void Checking(){
        if(ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(activity, "permission denied", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(activity, "don't message again", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}
