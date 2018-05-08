package com.example.tkw33.homework3modify;

import android.location.Location;
import android.util.Log;

import com.example.tkw33.homework3modify.GeoJson.LatLng;
import com.example.tkw33.homework3modify.GeoJson.LinkLatLng;
import com.example.tkw33.homework3modify.GeoJson.NodeLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Remote {
    public static String getData(String webURL) throws Exception {
        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(webURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        int responseCode = conn.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while( (dataLine = br.readLine()) != null ){
                result.append(dataLine);
            }
            br.close();
        } else {
            Log.d("response error", ""+responseCode);
        }
        return result.toString();
    }
    public static String getNode(String webURL) throws Exception {
        String read = "";
        StringBuffer buffer = new StringBuffer();
        URL nodeUrl = new URL(webURL);
        HttpURLConnection conn = (HttpURLConnection) nodeUrl.openConnection();
        conn.setRequestMethod("GET");
        int response = conn.getResponseCode();
        if (response == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            if ((read = br.readLine()) != null) {
                buffer.append(read);
            }

        }
        int ind = buffer.indexOf("bbox");
        buffer.replace(ind + "\"bbox\":".length() - 1, ind + "\"bbox\":".length(), "[{\"x\":\"");
        //"bbox": [{"x": "
        ind = buffer.indexOf(",", ind + "\"bbox\":".length());
        buffer.replace(ind, ind + 1, "\",\"y\":\"");
        //127.xxx","y": "
        ind = buffer.indexOf(",", ind + "\",\"y\":\"".length());
        buffer.replace(ind, ind + 1, "\"},{\"x\":\"");
        //30.xxx"},{"x": "
        ind = buffer.indexOf(",", ind + "\"x\":\"".length());
        buffer.replace(ind, ind + 1, "\",\"y\":\"");
        //127.xxx","y": "
        ind = buffer.indexOf("]", ind + "\",\"y\":\"".length());
        buffer.replace(ind, ind + 1, "\"}]");
        //30.xxx"}]

        int coindex = 0;
        while (coindex < buffer.length()) {
            coindex = buffer.indexOf("coordinates", coindex);
            if (coindex == -1)
                break;
            buffer.replace(coindex + "\"coordinates\":".length() - 1, coindex + "\"coordinates\":".length(),
                    "{\"x\":\"");
            //"coordinates": {"x": "
            coindex = buffer.indexOf(",", coindex + "\"coordinates\":".length());
            buffer.replace(coindex, coindex + 1, "\",\"y\":\"");
            //127.xxx","y": "
            coindex = buffer.indexOf("]", coindex + "\"coordinates\":".length());
            buffer.replace(coindex, coindex + 1, "\"}");
            //30.xxx"}
        }
        buffer.append("}}}");
        return buffer.toString();
    }
    public static String getLink(String webURL) throws Exception {
        String read = "";
        StringBuffer buffer = new StringBuffer();
        StringBuffer buf;
        String tmp;
        URL linkUrl = new URL(webURL);
        HttpURLConnection conn = (HttpURLConnection) linkUrl.openConnection();
        conn.setRequestMethod("GET");
        int response = conn.getResponseCode();
        if (response == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            if ((read = br.readLine()) != null) {
                buffer.append(read);
            }

        }
        int ind = buffer.indexOf("bbox");
        buffer.replace(ind + "\"bbox\":".length() - 1, ind + "\"bbox\":".length(), "[{\"x\":\"");
        //"bbox": [{"x": "
        ind = buffer.indexOf(",", ind + "\"bbox\":".length());
        buffer.replace(ind, ind + 1, "\",\"y\":\"");
        //127.xxx","y": "
        ind = buffer.indexOf(",", ind + "\",\"y\":\"".length());
        buffer.replace(ind, ind + 1, "\"},{\"x\":\"");
        //30.xxx"},{"x": "
        ind = buffer.indexOf(",", ind + "\"x\":\"".length());
        buffer.replace(ind, ind + 1, "\",\"y\":\"");
        //127.xxx","y": "
        ind = buffer.indexOf("]", ind + "\",\"y\":\"".length());
        buffer.replace(ind, ind + 1, "\"}]");
        //30.xxx"}]

        tmp = buffer.toString();
        int coindex = 0, last = 0;//, lbrackets = 0, rbrackets = 0;
        tmp = tmp.replaceAll("\\[\\[", "[");
        tmp = tmp.replaceAll("\\]\\]", "]");
        buf = new StringBuffer(tmp);
        /*while((lbrackets = buffer.indexOf("[[", lbrackets + 1)) != -1
                && (rbrackets = buffer.indexOf("]]", rbrackets + 1)) != -1) {
            //lbrackets = buffer.indexOf("[[", lbrackets + 1);
            buffer.replace(lbrackets, lbrackets+1, "");
            //rbrackets = buffer.indexOf("]]", rbrackets + 1);
            buffer.replace(rbrackets, rbrackets+1, "");
        }*/
        while(coindex < buf.length()) {
            last = buf.indexOf("properties", coindex+1);
            coindex = buf.indexOf("coordinates", coindex);
            if(coindex == -1 && last == -1)
                break;

            //buffer.replace(coindex + "\"coordinates\":".length(), coindex + "\"coordinates\":".length() + 2, "[");
            //"coordinates":[[[ -> "coordinates":[[
            coindex += "\"coordinates\":".length();

            while (coindex < last) {
                if(coindex == last-"\"}]},\"".length()) {
                    coindex = last;
                    break;
                }
                //if((change = buffer.indexOf("[[", change)) != -1)
                   // buffer.replace(change, change+2, "[");

//수정
                coindex = buf.indexOf("[", coindex);
                buf.replace(coindex, coindex + 1, "{\"x\":\"");
                //"coordinates": [{"x": "
                coindex = buf.indexOf(",", coindex);
                buf.replace(coindex, coindex + 1, "\",\"y\":\"");
                //127.xxx","y": "

                //if((change = buffer.indexOf("]]", change)) != -1)
                    //buffer.replace(change, change+2, "]");

                coindex = buf.indexOf("]", coindex);
                buf.replace(coindex, coindex + 1, "\"}");
                //30.xxx"}]},

                last = buf.indexOf("properties", coindex);
            }
        }

        buf.append("}}}");
        return buf.toString();
    }
    public static void GeoJsonParsing(String data, String select){
        int colength;
        double dist = 0.0;
        String _id, lat, lng;
        ArrayList<LatLng> list;
        Location locationS = new Location("point A");
        Location locationE = new Location("point B");
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONObject result = response.getJSONObject("result");
            JSONObject featureCollection = result.getJSONObject("featureCollection");
            JSONArray features = featureCollection.getJSONArray("features");

            int flength = features.length();

            if(select == "link") {
                for (int i = 0; i < flength; i++) {
                    JSONObject row = features.getJSONObject(i);
                    JSONObject geometry = row.getJSONObject("geometry");
                    JSONObject properties = row.getJSONObject("properties");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");
                    _id = properties.getString("link_id");
                    colength = coordinates.length();
                    list = new ArrayList<>();
                    for (int j = 0; j < colength; j++) {
                        JSONObject coRow = coordinates.getJSONObject(j);
                        lat = coRow.getString("y");
                        lng = coRow.getString("x");
                        LatLng latLng = new LatLng(Double.parseDouble(lng), Double.parseDouble(lat));

                        list.add(latLng);
                    }
                    for (int k = 0; k < list.size() - 1; k++) {

                        locationS.setLatitude(list.get(k).latitude);
                        locationS.setLongitude(list.get(k).longitude);


                        locationE.setLatitude(list.get(k + 1).latitude);
                        locationE.setLongitude(list.get(k + 1).longitude);

                        dist += locationS.distanceTo(locationE);
                        //dist += calcDistance(list.get(k).latitude, list.get(k).longitude,
                                //list.get(k + 1).latitude, list.get(k + 1).longitude);
                    }
                    LinkLatLng linkLatLng = new LinkLatLng(_id, list, dist);
                    LinkLatLng.llist.add(linkLatLng);
                    dist = 0.0;

                }
            }
            else if(select == "node"){
                for (int i = 0; i < flength; i++) {
                    JSONObject row = features.getJSONObject(i);
                    JSONObject geometry = row.getJSONObject("geometry");
                    JSONObject properties = row.getJSONObject("properties");
                    JSONObject coordinates = geometry.getJSONObject("coordinates");
                    _id = properties.getString("node_id");
                    lng = coordinates.getString("x");
                    lat = coordinates.getString("y");
                    //LatLng latlng = new LatLng(Double.parseDouble(lng), Double.parseDouble(lat));
                    //list2.add(latlng);
                    NodeLatLng nodeLatLng = new NodeLatLng(_id, Double.parseDouble(lat), Double.parseDouble(lng));
                    NodeLatLng.nlist.add(nodeLatLng);
                }

            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
    /*public static double calcDistance(double lat1, double lng1, double lat2, double lng2){
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI/180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lng1 - lng2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        //double rslt = Math.round(Math.round(ret) / 1000);
        //if(rslt == 0)
            double rslt = Math.round(ret);
        return rslt;
    }*/
}
