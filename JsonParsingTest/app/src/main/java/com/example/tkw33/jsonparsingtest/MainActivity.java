package com.example.tkw33.jsonparsingtest;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends ListActivity {
    ParsingTask parsingTask;
    //TextView json_txt;
    //TextView tv_purpose;
    String purpose, numberOfCameras, pixelOfCameras, agencyName, streetNameAddress;
    String latitude, longitude;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button parse_btn = (Button) findViewById(R.id.parsing_btn);
        Button list_up_btn = (Button) findViewById(R.id.list_up_btn);
        listView = (ListView) findViewById(android.R.id.list);
        parsingTask = new ParsingTask();
        parse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parsingTask.execute();
            }
        });
        list_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelectionFromTop(0, 0);
                //listView.smoothScrollToPosition(0);
            }
        });
    }
    public void taskRestart(){
        parsingTask = new ParsingTask();
        parsingTask.execute();
    }
    public class ParsingTask extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDlg;
        JsonItemData jsonItemData;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progressDlg = new ProgressDialog(MainActivity.this);
            progressDlg.setTitle("download");
            progressDlg.setMessage("DOWN");
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDlg.setCancelable(false);
            progressDlg.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONArray rows = new JSONArray(s);
                int length = rows.length();
                SetJsonItem.setLength(length);
                for(int i = 0; i<length; i++){
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
                ListAdapter listAdapter = new ListItemAdapter(MainActivity.this, jsonItemData.jsonArray);
                setListAdapter(listAdapter);
            }catch (JSONException e) {e.printStackTrace();}
            //progressDlg.dismiss();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result="";
            try{
                result=
                        Remote.getData("http://api.data.go.kr/openapi/cctv-std?serviceKey=hhHIleO3oGtNT85BxGLDZAvCM8fgqrCmr4MNymfVVnAovNwe36pZaSUf7pCfLzr32yBH61D43ZtvuNBgD%2Bx7PA%3D%3D&s_page=1&s_list=100&type=json");
            }catch (Exception e){
                Log.d("timeout", e.toString());
                this.cancel(true);
                taskRestart();
            }
            return result;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //progressDlg.dismiss();
            Log.d("canceled", "this asynctask canceled");
        }
    }

}
