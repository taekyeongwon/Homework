package com.example.tkw33.homework3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParsingActivity extends AsyncTask<Void, Void, String> {
    String purpose, numberOfCameras, pixelOfCameras, agencyName, streetNameAddress;
    String latitude, longitude;
    JsonItemData jsonItemData;
    MainActivity mainActivity = new MainActivity();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        try{
            JSONArray rows = new JSONArray(data);
            int length = rows.length();
            JsonItemData.length = length;
            for (int i = 0; i < length; i++){
                JSONObject result = (JSONObject) rows.get(i);
                purpose = result.getString("설치목적구분");
                numberOfCameras = result.getString("카메라대수");
                pixelOfCameras = result.getString("카메라화소수");
                agencyName = result.getString("관리기관명");
                streetNameAddress = result.getString("소재지도로명주소");
                latitude = result.getString("위도");
                longitude = result.getString("경도");
                jsonItemData = new JsonItemData(purpose, numberOfCameras,
                        pixelOfCameras, agencyName, streetNameAddress,
                        Double.parseDouble(latitude), Double.parseDouble(longitude));
                jsonItemData.makeArray();
            }
        } catch (JSONException e) {e.printStackTrace();}
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        try {
            result =
                    Remote.getData("http://api.data.go.kr/openapi/cctv-std?serviceKey=hhHIleO3oGtNT85BxGLDZAvCM8fgqrCmr4MNymfVVnAovNwe36pZaSUf7pCfLzr32yBH61D43ZtvuNBgD%2Bx7PA%3D%3D&s_page=1&s_list=100&type=json");
        }
        catch ( Exception e ){
            Log.d("timeout", e.toString());
            this.cancel(true);
            mainActivity.taskRestart();
        }
        return result;
    }

}
class Remote {
    public static String getData(String webURL) throws Exception {
        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(webURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(500);
        conn.setReadTimeout(500);

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
}