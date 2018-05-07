package com.example.tkw33.homework3modify.GeoJson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.tkw33.homework3modify.MainActivity;
import com.example.tkw33.homework3modify.Remote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DoLinkGeoJsonParsing extends AsyncTask<Integer, Integer, Void> {
    //private ProgressBar pb;
    private Context context;
    private GeoJsonCallBack mCallBack;
    Remote remote = new Remote();
    File saveFile;
    static int i = 1;
    //public static ArrayList<LinkLatLng> llist;
    //public ArrayList<LatLng> list = null;
    //private ArrayList<Double> distance = new ArrayList<>();
    //Location locationS = new Location("point A");
    //Location locationE = new Location("point B");
    int colength;
    String _id, lat, lng;
    File file;
    static int loop = 1;

    public DoLinkGeoJsonParsing(Context context, GeoJsonCallBack mCallBack) {
        this.context = context;
        this.mCallBack = mCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //pb = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        ///pb.setMax(86);
        //pb.setProgress(loop);
        MainActivity.pb.setMax(86);
        MainActivity.pb.setProgress(loop);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //pb.setProgress(values[0].intValue());
        MainActivity.pb.setProgress(values[0].intValue());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Toast.makeText(context, "다운로드 완료.", Toast.LENGTH_SHORT).show();
        mCallBack.onFinish(loop);
        //mCallBack.onFinish();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Toast.makeText(context, "다운로드 실패", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Integer... page) {
        synchronized (this) {
            int p = page[0].intValue();
            //while (p <= 2994) {

            String link = "";

            //LinkLatLng.llist = new ArrayList<>();
            //list = new ArrayList<>();
            double dist = 0.0;
            long start, end;
            start = System.currentTimeMillis();
            //while(loop<=p && isCancelled() == false) {
            String dirPath = Environment.getExternalStorageDirectory() + "/Link";
            file = new File(dirPath);
            // Log.v("FILEPATH : ",dirPath);
            File existFile = new File(dirPath + "/link86.txt");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!existFile.exists()) {
                try {
                    //노드 하나의 좌표로 geomFilter에 넣으면 8개 연결된 링크의 좌표가 나오는데 이렇게 노드의 개수만큼만 하면
                    //훨씬 절약 될 듯
                    link = Remote.getLink(
                            "http://api.vworld.kr/req/data?service=data" +
                                    "&request=GetFeature&data=LT_L_MOCTLINK" +
                                    "&key=E1D5AE1B-4457-3678-AB90-181118857DA8&page="+p+"&size=100" +
                                    "&geomFilter=BOX(126.153311,33.187779,126.940882,33.582154)" +
                                    "&domain=com.example.tkw33.homework3modify");
                    //
                    //(NodeLatLng.nlist.get(p - 1).lng - 0.0001) + "," +
                    //(NodeLatLng.nlist.get(p - 1).lat - 0.0001) + "," + (NodeLatLng.nlist.get(p - 1).lng + 0.0001) + "," +
                    //(NodeLatLng.nlist.get(p - 1).lat + 0.0001) +
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //String testStr = link;
                saveFile = new File(dirPath + "/link" + p + ".txt");
                try {
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    fos.write(link.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + dirPath + "/link" + p + ".txt")));

                }
            }
            if (file.listFiles().length > 0) {
                //    for (File f : file.listFiles()) {
                //String str = file.getName();
                //Log.v(null, "filename:"+str);
                //String loadPath = dirPath + "/" + str;
                String loadPath = dirPath + "/link" + p + ".txt";
                try {
                    FileInputStream fis = new FileInputStream(loadPath);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));

                    String content = "", temp = "";
                    while ((temp = bufferedReader.readLine()) != null) {
                        content += temp;
                    }
                    //Log.v(null, ""+content);
                    //Remote.GeoJsonParsing(content, "link");//->이부분을 asynctask안에 파일이 있을때와 없을때로 똑같이 나눠서 해보기.
                    Remote.GeoJsonParsing(content, "link");
                    // 메인스레드에서 돌아가서 너무 느림
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //llist = new ArrayList<>();

            //Remote.GeoJsonParsing(link);
            loop = p;
            publishProgress(loop);

            //}
            end = System.currentTimeMillis();
            Log.d("DOWNLOADING LINKTIME : ", "" + (end - start) / 1000.0);
            //((MapActivity) MapActivity.mContext).GeoJsonResultOfLink(llist);
        }
        return null;
    }

}
