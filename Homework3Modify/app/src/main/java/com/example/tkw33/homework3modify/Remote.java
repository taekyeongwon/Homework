package com.example.tkw33.homework3modify;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        int coindex = 0, last = 0, change = 0;
        while(coindex < buffer.length()) {
            last = buffer.indexOf("properties", coindex);
            ///if((change = buffer.indexOf("[[", coindex)) != -1)
              //  buffer.replace(change, change+2, "[");
           // if((change = buffer.indexOf("]]", coindex)) != -1)
              //  buffer.replace(change, change+2, "]");

            coindex = buffer.indexOf("coordinates", coindex);
            if(coindex == -1 && last == -1)
                break;

            //buffer.replace(coindex + "\"coordinates\":".length(), coindex + "\"coordinates\":".length() + 2, "[");
            //"coordinates":[[[ -> "coordinates":[[
            coindex += "\"coordinates\":".length();

            while (coindex < last) {
                if(coindex == last-"\"}},\"".length()) {
                    buffer.replace(coindex + 2, coindex + 4, "]");
                    //30.xxx"}]},
                    coindex = last;
                    break;
                }
                if((change = buffer.indexOf("[[", change)) != -1)
                    buffer.replace(change, change+3, "[");

//수정
                coindex = buffer.indexOf("[", coindex-1);
                buffer.replace(coindex+1, coindex + 1, "{\"x\":\"");
                //"coordinates": [{"x": "
                coindex = buffer.indexOf(",", coindex);
                buffer.replace(coindex, coindex + 1, "\",\"y\":\"");
                //127.xxx","y": "

                if((change = buffer.indexOf("]]", change)) != -1)
                    buffer.replace(change, change+3, "]");

                coindex = buffer.indexOf("]", coindex);
                buffer.replace(coindex, coindex + 1, "\"}");
                //30.xxx"}]]},

                last = buffer.indexOf("properties", coindex);
            }
        }

        buffer.append("}}}");
        return buffer.toString();
    }
}
