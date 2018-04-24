package com.example.tkw33.homework3modify;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JsonListAdapter extends ArrayAdapter<JsonItemData> {
    JsonItemData[] data;
    public JsonListAdapter(Context context, JsonItemData[] data){
        super(context, R.layout.list_item, data);
        this.data = data;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_roadname = (TextView) convertView.findViewById(R.id.tv_roadname);
            viewHolder.tv_roadname_code = (TextView) convertView.findViewById(R.id.tv_roadname_code);
            viewHolder.tv_english_roadname = (TextView) convertView.findViewById(R.id.tv_english_roadname);
            viewHolder.tv_start_point = (TextView) convertView.findViewById(R.id.tv_start_point);
            viewHolder.tv_end_point = (TextView) convertView.findViewById(R.id.tv_end_point);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_roadname.setText("도로명 : " + data[position].roadName);
        viewHolder.tv_roadname_code.setText("도로명코드 : " + data[position].roadNameCode);
        viewHolder.tv_english_roadname.setText("영문도로명 : " + data[position].englishRoadName);
        viewHolder.tv_start_point.setText("기점 : " + data[position].startPoint);
        viewHolder.tv_end_point.setText("종점 : " + data[position].endPoint);

        return convertView;
    }
    private static class ViewHolder {
        public TextView tv_roadname;
        public TextView tv_roadname_code;
        public TextView tv_english_roadname;
        public TextView tv_start_point;
        public TextView tv_end_point;
    }
}
