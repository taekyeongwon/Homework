package com.example.tkw33.homework3;

import android.util.Log;

public class JsonItemData {
    String purpose, numberOfCameras, pixelOfCameras, agencyName, streetNameAddress;
    double latitude, longitude;
    static int i = 0;

    public JsonItemData(){}
    public JsonItemData(String purpose, String numberOfCameras, String pixelOfCameras,
                        String agencyName, String streetNameAddress, double latitude, double longitude) {
        this.purpose = purpose;
        this.numberOfCameras = numberOfCameras;
        this.pixelOfCameras = pixelOfCameras;
        this.agencyName = agencyName;
        this.streetNameAddress = streetNameAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static JsonItemData[] jsonDataArray = new JsonItemData[MainActivity.arrayLength];
    public void makeArray(){
        try {
            jsonDataArray[i] = new JsonItemData(getPurpose(), getNumberOfCameras(), getPixelOfCameras(),
                    getAgencyName(), getStreetNameAddress(), getLatitude(), getLongitude());
            if(i < MainActivity.arrayLength-1)  //이 if문 없으면 ArrayIndexOutOfBoundsException 발생. 왜 발생하는지?
                i++;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Log.d("ArrayIndexOutOfBounds", e.toString()+" exception!");
            Log.d("index", ""+i);
        }
    }

    public String getPurpose(){ return purpose; }
    public void setPurpose(String purpose){this.purpose = purpose;}
    public String getNumberOfCameras(){ return numberOfCameras; }
    public void setNumberOfCameras(String numberOfCameras){this.numberOfCameras = numberOfCameras;}
    public String getPixelOfCameras(){ return pixelOfCameras; }
    public void setPixelOfCameras(String pixelOfCameras){this.pixelOfCameras = pixelOfCameras;}
    public String getAgencyName(){ return agencyName; }
    public void setAgencyName(String agencyName){this.agencyName = agencyName;}
    public String getStreetNameAddress(){ return streetNameAddress; }
    public void setStreetNameAddress(String streetNameAddress){this.streetNameAddress = streetNameAddress;}
    public double getLatitude(){ return latitude; }
    public void setLatitude(double latitude){this.latitude = latitude;}
    public double getLongitude(){ return longitude; }
    public void setLongitude(double longitude){this.longitude = longitude;}
}
