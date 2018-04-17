package com.example.tkw33.homework2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListView extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListItemData> mArrayList;
    LayoutInflater layoutInflater;
    public MyListView(Context context, ArrayList<ListItemData> arraylist) {
        this.mContext = context;
        this.mArrayList = arraylist;
        layoutInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.holdInfo = (TextView) convertView.findViewById(R.id.item_infoText);
            viewHolder.holdValue = (TextView) convertView.findViewById(R.id.item_valueText);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.holdInfo.setText(mArrayList.get(position).infoText);
        viewHolder.holdValue.setText(mArrayList.get(position).valueText);

        return convertView;
    }
    private static class ViewHolder {
        public TextView holdInfo;
        public TextView holdValue;
    }
}

