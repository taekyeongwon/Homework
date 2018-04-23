package com.example.tkw33.homework3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ListActivity{

    ListView listView;
    ParsingActivity parsingActivity;
    public static int arrayLength = 0;
    ImageButton list_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("my hash key : ", GetHashKey.myKey(this));

        list_up_btn = (ImageButton) findViewById(R.id.list_up_btn);
        listView = (ListView) findViewById(android.R.id.list);
        parsingActivity = new ParsingActivity();
        parsingActivity.execute();
        list_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelectionFromTop(0, 0);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra("Index", position);
        startActivity(mapIntent);
    }
    public void taskRestart(){
            parsingActivity = new ParsingActivity();
            parsingActivity.execute();
    }
    public class ParsingActivity extends AsyncTask<Void, Void, String> {
        String purpose, numberOfCameras, pixelOfCameras, agencyName, streetNameAddress;
        String latitude, longitude;
        JsonItemData jsonItemData;
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
                arrayLength = length;
                jsonItemData = new JsonItemData();
                for (int i = 0; i < length; i++){
                    JSONObject result = (JSONObject) rows.get(i);
                    purpose = result.getString("설치목적구분");
                    jsonItemData.setPurpose(purpose);

                    numberOfCameras = result.getString("카메라대수");
                    jsonItemData.setNumberOfCameras(numberOfCameras);

                    pixelOfCameras = result.getString("카메라화소수");
                    jsonItemData.setPixelOfCameras(pixelOfCameras);

                    agencyName = result.getString("관리기관명");
                    jsonItemData.setAgencyName(agencyName);

                    streetNameAddress = result.getString("소재지도로명주소");
                    jsonItemData.setStreetNameAddress(streetNameAddress);

                    latitude = result.getString("위도");
                    jsonItemData.setLatitude(Double.parseDouble(latitude));

                    longitude = result.getString("경도");
                    jsonItemData.setLongitude(Double.parseDouble(longitude));
                    jsonItemData.makeArray();
                }
                ListAdapter listAdapter = new JsonListAdapter(MainActivity.this, JsonItemData.jsonDataArray);
                setListAdapter(listAdapter);
            } catch (JSONException e) {e.printStackTrace();}
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("Canceled","this task is canceled");
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                result =
                        Remote.getData("http://api.data.go.kr/openapi/cctv-std?serviceKey=dmEedXgq4BTNi2NWl8xAAB05tSi7m6F5IdcxwpCWtgK7s%2FQ%2BQHM0floAgPXauZca%2BXhrm%2F6wkiuCn5956y54PQ%3D%3D&s_page=1&s_list=100&type=json");
            }
            catch ( Exception e ){
                Log.d("Timeout Exception", e.toString());
                cancel(true);
                taskRestart();
            }
            return result;
        }

    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "MainActivity destroyed");
    }*/
}
