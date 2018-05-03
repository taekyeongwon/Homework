package com.example.tkw33.homework3modify.GeoJson;

import java.util.ArrayList;

/*public class LinkLatLng {
    public double linkLineLat, linkLineLat2, linkLineLng, linkLineLng2;

    public LinkLatLng(double linkLineLat, double linkLineLng,
                      double linkLineLat2, double linkLineLng2) {
        this.linkLineLat = linkLineLat;
        this.linkLineLat2 = linkLineLat2;
        this.linkLineLng = linkLineLng;
        this.linkLineLng2 = linkLineLng2;
    }
}
*/
public class LinkLatLng {
    public String link_id;
    public ArrayList<LatLng> coordinate;
    public LinkLatLng(String link_id, ArrayList<LatLng> coordinate){
        this.link_id = link_id;
        this.coordinate = new ArrayList<>();
        this.coordinate = coordinate;
    }
}