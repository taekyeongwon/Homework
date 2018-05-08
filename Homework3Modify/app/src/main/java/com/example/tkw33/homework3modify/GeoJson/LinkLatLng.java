package com.example.tkw33.homework3modify.GeoJson;

import org.w3c.dom.Node;

import java.util.ArrayList;

public class LinkLatLng {
    public String link_id;
    public ArrayList<LatLng> coordinate;
    public double distance;
    public NodeLatLng node;
    public NodeLatLng nextNode;

    //public ArrayList<NodeLatLng> node;
    public LinkLatLng(){}
    /*public LinkLatLng(String link_id, ArrayList<LatLng> coordinate, double distance){
        this.link_id = link_id;
        this.coordinate = new ArrayList<>();
        this.coordinate = coordinate;
        this.distance = distance;
    }*/
    public static ArrayList<LinkLatLng> llist = new ArrayList<>();

    public void setLink_id(String link_id){this.link_id = link_id;}
    public String getLink_id(){return link_id;}
    public void setCoordinate(ArrayList<LatLng> coordinate){this.coordinate = new ArrayList<>();
    this.coordinate = coordinate;}
    public ArrayList<LatLng> getCoordinate() { return coordinate; }
    public void setDistance(double distance){this.distance = distance;}
    public double getDistance(){return distance;}

    public void setNode(NodeLatLng node){
        //this.node = new ArrayList<>();
        this.node = node;
    }
    public NodeLatLng getNode() { return node; }
    public void setNextNode(NodeLatLng nextNode) { this.nextNode = nextNode; }
    public NodeLatLng getNextNode() { return nextNode; }
}