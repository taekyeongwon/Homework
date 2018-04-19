package com.example.tkw33.kakaomaptest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<ListItemData> {
    public ListItemAdapter(Context context, ListItemData[] data){
        super(context, R.layout.list_item_data, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemData data = getItem(position);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_data, null);
        }
        TextView tvLat = (TextView) convertView.findViewById(R.id.lat_txt_data);
        TextView tvLon = (TextView) convertView.findViewById(R.id.lon_txt_data);

        tvLat.setText(data.lat);
        tvLon.setText(data.lon);

        return convertView;
    }
}
