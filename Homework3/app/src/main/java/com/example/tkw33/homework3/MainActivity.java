package com.example.tkw33.homework3;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ListActivity{

    ListView listView;
    ParsingActivity parsingActivity;
    public static int arrayLength = 0;
    ImageButton list_up_btn;

    static double tIndex=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("my hash key : ", GetHashKey.myKey(this));
        list_up_btn = (ImageButton) findViewById(R.id.list_up_btn);
        listView = (ListView) findViewById(android.R.id.list);
        for(int i = 0; i<10; i++) {
            //try {
                parsingActivity = new ParsingActivity();
                //Thread.sleep(500);
        //parsingActivity.execute();
                parsingActivity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
           // catch (InterruptedException e) {
           //     e.printStackTrace();
          //  }
        //}


        list_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelectionFromTop(0, 0);
                Toast.makeText(MainActivity.this, "avg time "+tIndex/10.0, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra("Index", position);
        startActivity(mapIntent);
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
                long start, end;
                start = System.currentTimeMillis();
                //JSONArray rows = new JSONArray(data);
                JSONObject jsonObject = new JSONObject(data);
                JSONObject SdeTlSprdManageW = jsonObject.getJSONObject("SdeTlSprdManageW");
                JSONArray row = SdeTlSprdManageW.getJSONArray("row");
                //int length = rows.length();
                int length = row.length();
                arrayLength = length;
                jsonItemData = new JsonItemData();
                for (int i = 0; i < length; i++){
                    JSONObject result = (JSONObject) row.get(i);
                    purpose = result.getString("RN");
                    jsonItemData.setPurpose(purpose);

                    numberOfCameras = result.getString("RN_CD");
                    jsonItemData.setNumberOfCameras(numberOfCameras);

                    pixelOfCameras = result.getString("ENG_RN");
                    jsonItemData.setPixelOfCameras(pixelOfCameras);

                    agencyName = result.getString("NTFC_DE");
                    jsonItemData.setAgencyName(agencyName);

                    streetNameAddress = result.getString("RN_DLB_DE");
                    jsonItemData.setStreetNameAddress(streetNameAddress);

                    latitude = result.getString("LAT");
                    jsonItemData.setLatitude(Double.parseDouble(latitude));

                    longitude = result.getString("LNG");
                    jsonItemData.setLongitude(Double.parseDouble(longitude));
                    jsonItemData.makeArray();
                }
                ListAdapter listAdapter = new JsonListAdapter(MainActivity.this, JsonItemData.jsonDataArray);
               setListAdapter(listAdapter);
                end = System.currentTimeMillis();
                tIndex += (end-start)/1000.0;
                Log.d("CurrentTimeMillis", "time : "+(end - start)/1000.0);
            } catch (JSONException e) {e.printStackTrace();}
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            boolean except = true;
            while(except != false) {
                try {
                    result =
                            //Remote.getData("http://api.data.go.kr/openapi/cctv-std?serviceKey=dmEedXgq4BTNi2NWl8xAAB05tSi7m6F5IdcxwpCWtgK7s%2FQ%2BQHM0floAgPXauZca%2BXhrm%2F6wkiuCn5956y54PQ%3D%3D&s_page=1&s_list=10&type=json");
                    Remote.getData("http://openapi.seoul.go.kr:8088/5276627355746b773636514879674c/json/SdeTlSprdManageW/1/1000/");
                            except = false;
                } catch (Exception e) {
                    Log.d("Timeout Exception", e.toString());
                    except = true;
                }
            }
            return result;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "MainActivity destroyed");
        System.exit(0);     //어플이 종료되어도 static영역의 JsonItemData.jsonDataArray는
                                   //남아있기 때문에 다시 실행했을 때 ArrayIndexOutOfBoundsException 발생.
                                   //따라서 앱이 Destroy 됐을 때 System.exit(0)로 프로세스를 종료시키고 모든 리소스를 반환.
    }

}
