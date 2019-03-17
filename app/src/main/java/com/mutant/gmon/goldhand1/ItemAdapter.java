package com.mutant.gmon.goldhand1;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    private static final String LOG_TAG = ItemAdapter.class.getSimpleName();

    public ItemAdapter(Activity context, ArrayList<Item> items){

        super(context,0,items);
    }
    public View getView(int position,View convertView,ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Item currentItem = getItem(position);
        TextView item_name = (TextView)listItemView.findViewById(R.id.list_item_name);
        ImageView item_image = (ImageView)listItemView.findViewById(R.id.list_item_icon);
        item_name.setText(currentItem.getText());
        item_image.setImageResource(currentItem.getResource_id());

        return listItemView;
    }
}