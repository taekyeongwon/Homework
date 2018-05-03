package com.example.tkw33.homework3modify.DB;

import com.example.tkw33.homework3modify.GeoJson.LatLng;

import java.util.ArrayList;

public class ParsingData {
    public int _id;
    public String link_id, slat, slng, elat, elng;
    public double dist;
    //public ArrayList<LatLng> slatLng;
    //public ArrayList<LatLng> elatLng;
    public ParsingData(int _id, String link_id, String slat, String slng,
                       String elat, String elng){//, ArrayList<LatLng> slatLng, ArrayList<LatLng> elatLng) {
        this._id = _id;
        this.link_id = link_id;
        //this.slatLng = new ArrayList<>();
        //this.elatLng = new ArrayList<>();
        //this.slatLng = slatLng;
        //this.elatLng = elatLng;
        this.slat = slat;
        this.slng = slng;
        this.elat = elat;
        this.elng = elng;
    }
    public double getDist(){
        return dist;
    }
    public void setDist(double dist){
        this.dist = dist;
    }
}
