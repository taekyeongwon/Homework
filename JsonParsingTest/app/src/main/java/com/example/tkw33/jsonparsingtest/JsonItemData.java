package com.example.tkw33.jsonparsingtest;

import android.util.Log;

public class JsonItemData {
    String purpose, numberOfCameras, pixelOfCameras, agencyName, streetNameAddress;
    double latitude, longitude;
    static int i=0;

    public JsonItemData(String purpose, String numberOfCameras, String pixelOfCameras,
                        String agencyName, String streetNameAddress, double latitude, double longitude){
        this.purpose = purpose;
        this.numberOfCameras = numberOfCameras;
        this.pixelOfCameras = pixelOfCameras;
        this.agencyName = agencyName;
        this.streetNameAddress = streetNameAddress;
        this.latitude = latitude;
        this.longitude = longitude;

    }
    public static JsonItemData[] jsonArray = new JsonItemData[SetJsonItem.getLength()];

    public void makeArray(){
        jsonArray[i] = new JsonItemData(purpose, numberOfCameras, pixelOfCameras,
                agencyName, streetNameAddress, latitude, longitude);
        i++;
        //Log.d("json array lat", String.valueOf(latitude));
        //Log.d("json array address", streetNameAddress);
    }
}
