package com.example.tkw33.homework3modify.GeoJson;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tkw33.homework3modify.DB.DBHelper;
import com.example.tkw33.homework3modify.MapActivity;
import com.example.tkw33.homework3modify.Remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoLinkGeoJsonParsing extends AsyncTask<Integer, Integer, Void> {
    private ProgressBar pb;
    private Context context;
    private GeoJsonCallBack mCallBack;
    public ArrayList<LinkLatLng> llist = null;
    public ArrayList<LatLng> list = null;
    int colength;
    String _id, lat, lng;
    int loop = 1;

    public DoLinkGeoJsonParsing(Context context, GeoJsonCallBack mCallBack){
        this.context = context;
        this.mCallBack = mCallBack;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        pb.setMax(86);
        pb.setProgress(loop);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pb.setProgress(values[0].intValue());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "다운로드 완료.", Toast.LENGTH_SHORT).show();
        mCallBack.onFinish(llist);
    }

    @Override
    protected Void doInBackground(Integer... page) {
        int p = page[0].intValue();
        String link = "";

        llist = new ArrayList<>();
        list = new ArrayList<>();
        while(loop<=p && isCancelled() == false) {
            try {
                link = Remote.getLink(
                        "http://api.vworld.kr/req/data?service=data" +
                                "&request=GetFeature&data=LT_L_MOCTLINK" +
                                "&key=E1D5AE1B-4457-3678-AB90-181118857DA8&page=" + loop + "&size=100" +
                                "&geomFilter=BOX(126.153311,33.187779,126.940882,33.582154)" +
                                "&domain=com.example.tkw33.homework3modify");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //llist = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(link);
                JSONObject response = jsonObject.getJSONObject("response");
                JSONObject result = response.getJSONObject("result");
                JSONObject featureCollection = result.getJSONObject("featureCollection");
                JSONArray features = featureCollection.getJSONArray("features");

                int flength = features.length();

                for (int i = 0; i < flength; i++) {
                    JSONObject row = features.getJSONObject(i);
                    JSONObject geometry = row.getJSONObject("geometry");
                    JSONObject properties = row.getJSONObject("properties");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");
                    _id = properties.getString("link_id");
                    colength = coordinates.length();
                    for(int j = 0; j < colength; j++){
                        JSONObject coRow = coordinates.getJSONObject(j);
                        lat = coRow.getString("y");
                        lng = coRow.getString("x");
                        LatLng latLng = new LatLng(Double.parseDouble(lng), Double.parseDouble(lat));
                        list.add(latLng);
                    }
                    LinkLatLng linkLatLng = new LinkLatLng(_id, list);
                    llist.add(linkLatLng);
                    //llist.add(list);
                    /*JSONObject coRow = coordinates.getJSONObject(0);
                    lxLng = coRow.getString("x");
                    lyLat = coRow.getString("y");
                    coRow = coordinates.getJSONObject(1);
                    lxLng2 = coRow.getString("x");
                    lyLat2 = coRow.getString("y");

                    LinkLatLng latlng = new LinkLatLng(Double.parseDouble(lxLng), Double.parseDouble(lyLat),
                            Double.parseDouble(lxLng2), Double.parseDouble(lyLat2));
                    llist.add(latlng);*/

                }
                //((MapActivity) MapActivity.mContext).GeoJsonResultOfLink(llist);
            } catch (JSONException je) {
                je.printStackTrace();
            }
            loop++;
            publishProgress(loop);
        }
        //((MapActivity) MapActivity.mContext).GeoJsonResultOfLink(llist);
        return null;
    }
}
