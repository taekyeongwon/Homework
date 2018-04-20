package com.example.tkw33.jsonparsingtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<JsonItemData> {
    //SetJsonItem jsonItem;

    public ListItemAdapter(Context context, JsonItemData[] data){
        super(context, R.layout.list_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JsonItemData[] data = JsonItemData.jsonArray;
        if(convertView == null){
            //ScrollView scrollView = (ScrollView) convertView.findViewById(R.id.scroll_view);
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView tv_purpose = (TextView) convertView.findViewById(R.id.tv_purpose);
        TextView tv_numberOfCameras = (TextView) convertView.findViewById(R.id.tv_number_of_cameras);
        TextView tv_pixelOfCameras = (TextView) convertView.findViewById(R.id.tv_pixel_of_cameras);
        TextView tv_agencyName = (TextView) convertView.findViewById(R.id.tv_agency_name);
        TextView tv_streetNameAddress = (TextView) convertView.findViewById(R.id.tv_street_name);

        //Log.d("adapter 설치목적 : ", data[position].purpose);
        tv_purpose.setText("설치목적 : " + data[position].purpose);
        tv_numberOfCameras.setText("카메라대수 : " + data[position].numberOfCameras);
        tv_pixelOfCameras.setText("카메라화소수 : " + data[position].pixelOfCameras);
        tv_agencyName.setText("관리기관명 : " + data[position].agencyName);
        tv_streetNameAddress.setText("소재지도로명주소 : " + data[position].streetNameAddress);

        return convertView;
    }
}
