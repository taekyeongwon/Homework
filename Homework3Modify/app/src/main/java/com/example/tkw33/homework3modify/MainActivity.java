package com.example.tkw33.homework3modify;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tkw33.homework3modify.DB.MakeGraph;
import com.example.tkw33.homework3modify.GeoJson.DoLinkGeoJsonParsing;
import com.example.tkw33.homework3modify.GeoJson.DoNodeGeoJsonParsing;
import com.example.tkw33.homework3modify.GeoJson.GeoJsonCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.tkw33.homework3modify.DB.DBHelper;


public class MainActivity extends ListActivity {

    ListView listView;
    ParsingTask parsingTask;
    ImageButton list_up_btn;
    JsonItemData[] jsonItemArray;
    Button download_btn;
    //DBHelper dbHelper = null;
    //SQLiteDatabase database;
    public static ProgressBar pb;
    boolean granted = false;
    int size = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MyHashKey : ", GetHashKey.myKey(this));
        list_up_btn = (ImageButton) findViewById(R.id.list_up_btn);
        download_btn = (Button) findViewById(R.id.download_btn);
        listView = (ListView) findViewById(android.R.id.list);
        pb = (ProgressBar) findViewById(R.id.pb);

        if(Build.VERSION.SDK_INT >= 23) {
            PermissionCheck permissionCheck = new PermissionCheck(MainActivity.this,
                    "storage");
            permissionCheck.requestPermission();
        }
        parsingTask = new ParsingTask();
        parsingTask.execute();
        list_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelectionFromTop(0, 0);
            }
        });
        //myTask();
        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String dirPath = Environment.getExternalStorageDirectory()+"/test";
                //final File file = new File(dirPath);
                    myTask();
                    }

        });
    }

    public void myTask(){
        if (granted) {
            for(int i = 1; i<=30; i++){
                DoNodeGeoJsonParsing geoJsonParsing = new DoNodeGeoJsonParsing(MainActivity.this, new GeoJsonCallBack() {
                    @Override
                    public void onFinish(int loop) {
                        if (loop == 30){
                            for (int i = 1; i<=86; i++) {
                                DoLinkGeoJsonParsing geoJsonParsing = new DoLinkGeoJsonParsing(MainActivity.this, new GeoJsonCallBack() {
                                    @Override
                                    public void onFinish(int loop) {
                                        if (loop == 86) {
                                            //MakeGraph mGraph = new MakeGraph();
                                            Toast.makeText(MainActivity.this, "done.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                geoJsonParsing.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, i);
                            }
                        }
                    }
                });
                geoJsonParsing.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, i);
            }
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        JsonItemData jsonItemData = jsonItemArray[position];
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra("JsonData", jsonItemData);
        startActivity(mapIntent);
    }

    public class ParsingTask extends AsyncTask<Void, Void, String> {
        String roadName, roadNameCode, englishRoadName, startPoint, endPoint;
        String latitude, longitude;
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            boolean except = true;
            while(except != false) {
                try {
                    result =
                            Remote.getData("http://openapi.seoul.go.kr:8088/5276627355746b773636514879674c/json/SdeTlSprdManageW/1/100/");
                    except = false;
                } catch (Exception e) {
                    Log.d("Timeout Exception", e.toString());
                    except = true;
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            try{
                long start, end;
                start = System.currentTimeMillis();

                JSONObject jsonObject = new JSONObject(data);
                JSONObject field = jsonObject.getJSONObject("SdeTlSprdManageW");
                JSONArray row = field.getJSONArray("row");
                int length = row.length();

                jsonItemArray = new JsonItemData[length];
                for (int i = 0; i < length; i++){
                    jsonItemArray[i] = new JsonItemData();
                    JSONObject result = (JSONObject) row.get(i);
                    roadName = result.getString("RN");
                    jsonItemArray[i].roadName = roadName;

                    roadNameCode = result.getString("RN_CD");
                    jsonItemArray[i].roadNameCode = roadNameCode;

                    englishRoadName = result.getString("ENG_RN");
                    jsonItemArray[i].englishRoadName = englishRoadName;

                    startPoint = result.getString("RBP_CN");
                    jsonItemArray[i].startPoint = startPoint;

                    endPoint = result.getString("REP_CN");
                    jsonItemArray[i].endPoint = endPoint;

                    latitude = result.getString("LAT");
                    jsonItemArray[i].latitude = Double.parseDouble(latitude);

                    longitude = result.getString("LNG");
                    jsonItemArray[i].longitude = Double.parseDouble(longitude);

                }
                ListAdapter listAdapter = new JsonListAdapter(MainActivity.this, jsonItemArray);
                setListAdapter(listAdapter);

                end = System.currentTimeMillis();
                Log.d("CurrentTimeMillis", "time : "+(end - start)/1000.0);

            } catch (JSONException e) {e.printStackTrace();}
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2 : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("onRequestResult granted", "result granted");
                    granted = true;
                } else {
                    Log.d("onRequestResult denied", "result denied");
                    granted = false;
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
