/*package com.example.tkw33.homework3modify.GeoJson;

import android.os.AsyncTask;

import com.example.tkw33.homework3modify.GeoJson.LatLng;
import com.example.tkw33.homework3modify.MapActivity;
import com.example.tkw33.homework3modify.Remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoNodeGeoJsonParsing extends AsyncTask<Integer, Void, Void> {
        List<LatLng> list;
        String xLng, yLat;
        @Override
        protected Void doInBackground(Integer... page) {
            int p = page[0].intValue();

            String node = "";
            int loop = 1;
            list = new ArrayList<>();
            while (loop <= p) {
                try {
                    node = Remote.getNode(
                            "http://api.vworld.kr/req/data?service=data" +
                            "&request=GetFeature&data=LT_P_MOCTNODE" +
                            "&key=E1D5AE1B-4457-3678-AB90-181118857DA8&page=" + loop + "&size=100" +
                            "&geomFilter=BOX(126.153311,33.187779,126.940882,33.582154)" +
                            "&domain=com.example.tkw33.homework3modify");
                } catch (Exception e) { e.printStackTrace(); }

                //list = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(node);
                    JSONObject response = jsonObject.getJSONObject("response");
                    JSONObject result = response.getJSONObject("result");
                    JSONObject featureCollection = result.getJSONObject("featureCollection");
                    JSONArray features = featureCollection.getJSONArray("features");

                    int len = features.length();

                    for (int i = 0; i < len; i++) {
                        JSONObject row = features.getJSONObject(i);
                        JSONObject geometry = row.getJSONObject("geometry");
                        JSONObject coordinates = geometry.getJSONObject("coordinates");
                        xLng = coordinates.getString("x");
                        yLat = coordinates.getString("y");
                        LatLng latlng = new LatLng(Double.parseDouble(xLng), Double.parseDouble(yLat));
                        list.add(latlng);
                    }
                    //((MapActivity) MapActivity.mContext).GeoJsonResultOfNode(list);

                } catch (JSONException je) {
                    je.printStackTrace();
                }
                loop++;
            }
            ((MapActivity) MapActivity.mContext).GeoJsonResultOfNode(list);
            return null;
        }

}*/
