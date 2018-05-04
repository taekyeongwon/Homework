package com.example.tkw33.homework3modify.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.tkw33.homework3modify.GeoJson.DoLinkGeoJsonParsing;
import com.example.tkw33.homework3modify.GeoJson.GeoJsonCallBack;
import com.example.tkw33.homework3modify.GeoJson.LatLng;
import com.example.tkw33.homework3modify.GeoJson.LinkLatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
    public Context context;
    //private LinkedList<Float> distance = new LinkedList<>();

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE MY_TABLE ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" LINK_ID TEXT, ");
        sb.append(" SLAT TEXT, ");
        sb.append(" SLNG TEXT, ");
        sb.append(" ELAT TEXT, ");
        sb.append(" ELNG TEXT, ");
        sb.append(" DIST TEXT ) ");
        db.execSQL(sb.toString());
        Toast.makeText(context, "table 생성 완료", Toast.LENGTH_SHORT).show();
        //DoLinkGeoJsonParsing.llist
        DoLinkGeoJsonParsing geoJsonParsing = new DoLinkGeoJsonParsing(context, new GeoJsonCallBack() {
            @Override
            public void onFinish(ArrayList<LinkLatLng> llist) {
                SQLiteDatabase sql = getWritableDatabase();
                sql.beginTransaction();
                try{
                    for(int i = 0; i < llist.size(); i++){
                        for(int j = 0; j < llist.get(i).coordinate.size() - 1; j++) {
                            ContentValues cv = new ContentValues();
                            cv.put("LINK_ID", llist.get(i).link_id);
                            cv.put("SLAT", llist.get(i).coordinate.get(j).latitude);
                            cv.put("SLNG", llist.get(i).coordinate.get(j).longitude);
                            cv.put("ELAT", llist.get(i).coordinate.get(j + 1).latitude);
                            cv.put("ELNG", llist.get(i).coordinate.get(j + 1).longitude);
                            cv.put("DIST", llist.get(i).distance.get(j));
                            sql.insert("MY_TABLE", null, cv);
                        }
                    }
                    sql.setTransactionSuccessful();
                }
                catch (SQLException sqle) {sqle.printStackTrace();}
                finally {
                    sql.endTransaction();
                }
            }

            @Override
            public void onException() {

            }
        });
        geoJsonParsing.execute(86);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<ParsingData> getDBData(){
        ArrayList<ParsingData> data = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        int _id;
        int size = 0;
        String link_id, slat, slng, elat, elng, dist;
        sb.append(" SELECT _ID, LINK_ID, SLAT, SLNG, ELAT, ELNG, DIST FROM MY_TABLE");

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while(cursor.moveToNext()) {
            _id = cursor.getInt(0);
            link_id = cursor.getString(1);
            slat = cursor.getString(2);
            slng = cursor.getString(3);
            elat = cursor.getString(4);
            elng = cursor.getString(5);
            dist = cursor.getString(6);
            Log.d("GETDBDATA", ""+slat+", "+slng);
            ParsingData parsingData = new ParsingData(_id, link_id, Double.parseDouble(slat),
                    Double.parseDouble(slng), Double.parseDouble(elat), Double.parseDouble(elng), Double.parseDouble(dist));
            data.add(parsingData);

        }
        //return data;
        //getDistance(data);
        //size = data.size();
        //for(int i = 0; i < size; i++){
            //data.get(i).setDist(distance.get(i));
        //}
        return data;
    }
    /*public void getDistance(ArrayList<ParsingData> link){
        String link_id;
        for(int i = 0; i < link.size(); i++) {
            link_id = link.get(i).link_id;
            //for (int j = 0; j < link.size() - 1; j++) {
                if (link_id == link.get(i + 1).link_id){
                    Location locationS = new Location("point A");
                    locationS.setLatitude(link.get(j).slat);
                    locationS.setLongitude(link.get(j).slng);

                    Location locationE = new Location("point B");
                    locationE.setLatitude(link.get(j).elat);
                    locationE.setLongitude(link.get(j).elng);

                    distance.add(locationS.distanceTo(locationE));

                    //link.get(j).setDist(locationS.distanceTo(locationE));
                    Log.d("DISTANCE", " "+locationS.distanceTo(locationE));
                }
            //}
        }
        //return distance;
    }*/
}
