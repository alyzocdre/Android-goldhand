package com.mutant.gmon.goldhand1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Sell extends Fragment {
    private String phone = null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v =  inflater.inflate(R.layout.fragment_sell, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            phone = bundle.getString("contact");
        }

        final ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Education",R.drawable.book));
        items.add(new Item("Clothes",R.drawable.cloth));
        items.add(new Item("Food",R.drawable.food));
        items.add(new Item("Others",R.drawable.place));

        ItemAdapter itemAdapter = new ItemAdapter(getActivity(),items);
        ListView listView = v.findViewById(R.id.list_view_items);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item category = items.get(position);
                Intent intent = new Intent(getContext(),SellDetail.class);
                //startActivity(intent);
                switch(position){
                    case 0:
                        intent.putExtra("imgCategory",R.drawable.book);
                        intent.putExtra("productCateg","Education");
                        break;
                    case 1:
                        intent.putExtra("imgCategory",R.drawable.cloth);
                        intent.putExtra("productCateg","Clothes");
                        break;
                    case 2:
                        intent.putExtra("imgCategory",R.drawable.food);
                        intent.putExtra("productCateg","Food");
                        break;
                    case 3:
                        intent.putExtra("imgCategory",R.drawable.place);
                        intent.putExtra("productCateg","Others");
                        break;



                }
                intent.putExtra("contact",phone);
                startActivity(intent);
                //Toast.makeText(getContext(),"you clicked"+category.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        getActivity().setTitle("Sell");
    }
}
