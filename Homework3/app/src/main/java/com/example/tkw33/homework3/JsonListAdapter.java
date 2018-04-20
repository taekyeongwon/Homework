package com.example.tkw33.homework3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class JsonListAdapter extends ArrayAdapter<JsonItemData> {
    public JsonListAdapter(Context context,JsonItemData[] data){
        super(context, R.layout.list_item, data);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        JsonItemData[] data = JsonItemData.jsonDataArray;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_purpose = (TextView) convertView.findViewById(R.id.tv_purpose);
            viewHolder.tv_numberOfCameras = (TextView) convertView.findViewById(R.id.tv_number_of_cameras);
            viewHolder.tv_pixelOfCameras = (TextView) convertView.findViewById(R.id.tv_pixel_of_cameras);
            viewHolder.tv_agencyName = (TextView) convertView.findViewById(R.id.tv_agency_name);
            viewHolder.tv_streetNameAddress = (TextView) convertView.findViewById(R.id.tv_street_name);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_purpose.setText("설치목적 : " + data[position].purpose);
        viewHolder.tv_numberOfCameras.setText("카메라대수 : " + data[position].numberOfCameras);
        viewHolder.tv_pixelOfCameras.setText("카메라화소수 : " + data[position].pixelOfCameras);
        viewHolder.tv_agencyName.setText("관리기관명 : " + data[position].agencyName);
        viewHolder.tv_streetNameAddress.setText("소재지도로명주소 : " + data[position].streetNameAddress);

        return convertView;
    }
    private static class ViewHolder {
        public TextView tv_purpose;
        public TextView tv_numberOfCameras;
        public TextView tv_pixelOfCameras;
        public TextView tv_agencyName;
        public TextView tv_streetNameAddress;
    }
}
