package com.example.tkw33.homework3modify.GeoJson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.tkw33.homework3modify.Remote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DoNodeGeoJsonParsing extends AsyncTask<Integer, Void, Void> {
        //ArrayList<LatLng> list;
        File file;
        File saveFile;
        int loop;
        private Context context;
        GeoJsonCallBack mCallBack;
        Remote remote = new Remote();
        public DoNodeGeoJsonParsing(Context context, GeoJsonCallBack mCallBack){
            this.context = context;
            this.mCallBack = mCallBack;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallBack.onFinish(loop);
    }

    @Override
        protected Void doInBackground(Integer... page) {
        synchronized (this) {
            int p = page[0].intValue();
            long start, end;
            start = System.currentTimeMillis();
            String node = "";
            //int loop = 1;
            //list = new ArrayList<>();
           // NodeLatLng.nlist = new ArrayList<>();
            //while (loop <= p) {
            String dirPath = Environment.getExternalStorageDirectory() + "/Node";
            file = new File(dirPath);
            File existFile = new File(dirPath + "/node30.txt");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!existFile.exists()) {
                try {
                    node = Remote.getNode(
                            "http://api.vworld.kr/req/data?service=data" +
                                    "&request=GetFeature&data=LT_P_MOCTNODE" +
                                    "&key=E1D5AE1B-4457-3678-AB90-181118857DA8&page=" + p + "&size=100" +
                                    "&geomFilter=BOX(126.153311,33.187779,126.940882,33.582154)" +
                                    "&domain=com.example.tkw33.homework3modify");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File saveFile = new File(dirPath + "/node" + p + ".txt");
                try {
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    fos.write(node.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + dirPath + "/node" + p + ".txt")));

                }
            }
            //list = new ArrayList<>();

            if (file.listFiles().length > 0) {
                //for (File f : file.listFiles()) {
                    //String str = file.getName();
                    //Log.v(null, "filename:"+str);
                    //String loadPath = dirPath + "/" + str;
                String loadPath = dirPath + "/node" + p + ".txt";
                    try {
                        FileInputStream fis = new FileInputStream(loadPath);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));

                        String content = "", temp = "";
                        while ((temp = bufferedReader.readLine()) != null) {
                            content += temp;
                        }
                        //Log.v(null, ""+content);
                        //Remote.GeoJsonParsing(content, "node");
                        Remote.GeoJsonParsing(content, "node");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                loop = p;
            //loop++;
            //}
            //           ((MapActivity) MapActivity.mContext).GeoJsonResultOfNode(list);
            end = System.currentTimeMillis();
            Log.d("DOWNLOADING NODETIME : ", "" + (end - start) / 1000.0);
            return null;
        }
    }

}