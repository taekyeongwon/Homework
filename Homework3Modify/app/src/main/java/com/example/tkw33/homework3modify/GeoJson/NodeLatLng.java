package com.example.tkw33.homework3modify.GeoJson;

import java.util.ArrayList;

public class NodeLatLng {
    public String node_id;
    public double lat;
    public double lng;
    //public String link_tail;
    //public String link_head;
    public ArrayList<LinkLatLng> link;
    public NodeLatLng() {

    }
    public NodeLatLng(String node_id, double lat, double lng){
        this.node_id = node_id;
        this.lat = lat;
        this.lng = lng;
    }
    public static ArrayList<NodeLatLng> nlist = new ArrayList<>();
    public void setLink(ArrayList<LinkLatLng> link){
        //this.link.add(link);
        this.link = new ArrayList<>();
        for(int i=0; i<link.size(); i++) {
            //this.link = link;
            this.link.add(link.get(i));
        }
    }

    public ArrayList<LinkLatLng> getLink() {
        return link;
    }
}
