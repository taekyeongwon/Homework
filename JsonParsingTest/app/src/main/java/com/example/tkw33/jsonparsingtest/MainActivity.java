package com.example.tkw33.jsonparsingtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ParsingTask parsingTask;
    TextView json_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        json_txt = (TextView) findViewById(R.id.json_txt);
        Button parse_btn = (Button) findViewById(R.id.parsing_btn);
        parsingTask = new ParsingTask();
        parse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parsingTask.execute();
            }
        });
    }
    public class ParsingTask extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDlg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = new ProgressDialog(MainActivity.this);
            progressDlg.setTitle("download");
            progressDlg.setMessage("DOWN");
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDlg.setCancelable(false);
            progressDlg.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            StringBuffer sb = new StringBuffer();
            try{
                JSONObject json = new JSONObject();

                JSONArray rows = json.getJSONArray(s);
                int length = rows.length();
                for(int i = 0; i<length; i++){
                    JSONObject result = (JSONObject) rows.get(i);
                    String name = result.getString("설치목적구분");
                    Log.d("field", name);
                    sb.append(name+"\n");
                }
            }catch (JSONException e) {e.printStackTrace();}
            json_txt.setText(sb.toString());
            progressDlg.dismiss();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result="";
            try{
                result=
                        Remote.getData("http://api.data.go.kr/openapi/cctv-std?serviceKey=hhHIleO3oGtNT85BxGLDZAvCM8fgqrCmr4MNymfVVnAovNwe36pZaSUf7pCfLzr32yBH61D43ZtvuNBgD%2Bx7PA%3D%3D&s_page=1&s_list=10&type=json");

                Log.d("getData", result);
            }catch (Exception e){e.printStackTrace();}
            return result;
        }
    }
}
