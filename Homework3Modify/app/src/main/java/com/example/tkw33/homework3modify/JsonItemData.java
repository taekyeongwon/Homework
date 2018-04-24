package com.example.tkw33.homework3modify;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonItemData implements Parcelable{
    String roadName, roadNameCode, englishRoadName, startPoint, endPoint;
    double latitude, longitude;

    public JsonItemData(){

    }
    protected JsonItemData(Parcel in) {
        roadName = in.readString();
        roadNameCode = in.readString();
        englishRoadName = in.readString();
        startPoint = in.readString();
        endPoint = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<JsonItemData> CREATOR = new Creator<JsonItemData>() {
        @Override
        public JsonItemData createFromParcel(Parcel in) {
            return new JsonItemData(in);
        }

        @Override
        public JsonItemData[] newArray(int size) {
            return new JsonItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roadName);
        dest.writeString(roadNameCode);
        dest.writeString(englishRoadName);
        dest.writeString(startPoint);
        dest.writeString(endPoint);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
