package com.example.tkw33.homework3;

public class JsonItemData {
    String purpose, numberOfCameras, pixelOfCameras, agencyName,
    streetNameAddress;
    double latitude, longitude;
    static int i = 0;
    static int length;

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
    public static JsonItemData[] jsonDataArray = new JsonItemData[length];

    public void makeArray(){
        jsonDataArray[i] = new JsonItemData(purpose, numberOfCameras, pixelOfCameras,
                agencyName, streetNameAddress, latitude, longitude);
        i++;
    }

    /*public static void setLength(int length){
        JsonItemData.length = length;
    }
    public static int getLength(){
        return length;
    }*/
}
