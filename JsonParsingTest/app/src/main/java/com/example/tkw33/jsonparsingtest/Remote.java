package com.example.tkw33.jsonparsingtest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Remote {
    private static final String TAG = "ResponseCode : ";
    public static String getData(String webURL) throws Exception {
        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(webURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(500);
        conn.setReadTimeout(500);

        //conn.setUseCaches(false);
        //conn.setDefaultUseCaches(false);
        int responseCode = conn.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while( (dataLine = br.readLine()) != null){
                result.append(dataLine);
            }
            br.close();
        } else {
            Log.i(TAG, ""+responseCode);
        }
        return result.toString();
    }
}
