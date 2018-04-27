package com.example.tkw33.homework3modify;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonParsing extends AsyncTask<Integer, Void, Void> {
        List<LatLng> list;
        String xLng, yLat;
        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);

        }

        @Override
        protected Void doInBackground(Integer... page) {
            synchronized (this) {
                String read = "";
                StringBuffer buffer = new StringBuffer();
                int p = page[0].intValue();
                try {
                    URL url = new URL("http://api.vworld.kr/req/data?service=data" +
                            "&request=GetFeature&data=LT_P_MOCTNODE" +
                            "&key=E1D5AE1B-4457-3678-AB90-181118857DA8&page="+p+"&size=100" +
                            "&geomFilter=BOX(126.153311,33.187779,126.940882,33.582154)" +
                            "&domain=com.example.tkw33.homework3modify");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int response = conn.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        if ((read = br.readLine()) != null) {
                            buffer.append(read);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                String s = buffer.toString();
                list = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(s);
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
                    ((MapActivity) MapActivity.mContext).GeoJsonResult(list);

                } catch (JSONException je) {
                    je.printStackTrace();
                }
                return null;
            }
        }

}
