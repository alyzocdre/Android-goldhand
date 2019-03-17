package com.mutant.gmon.goldhand1;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.glideimageview.GlideImageView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static final String LOG_TAG = ItemAdapter.class.getSimpleName();

    public ProductAdapter(Activity context, ArrayList<Product> products){

        super(context,0,products);
    }
    public View getView(int position,View convertView,ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.buy_list_item,parent,false);
        }
        Product currentProduct = getItem(position);
        TextView item_name = (TextView)listItemView.findViewById(R.id.buy_list_item_name);
        TextView item_phone = (TextView)listItemView.findViewById(R.id.buy_list_item_phone);
        GlideImageView item_image = (GlideImageView)listItemView.findViewById(R.id.buy_list_item_icon);
        item_image.loadImageUrl(currentProduct.getImgUrl());
        String s = currentProduct.getImgUrl();
       /* String phone = "";
        String name = "";
        boolean num = true;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) <=9 && s.charAt(i) >=0 && num == true){
                phone += s.charAt(i);
            }
            else{
                num = false;
                name += s.charAt(i);
            }
        }*/
        item_name.setText(currentProduct.getName());
        item_phone.setText(currentProduct.getPhone());
        //item_image.setImageResource(currentProduct.getResource_id());

        return listItemView;
    }
}

