package com.mutant.gmon.goldhand1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BuyDetail extends AppCompatActivity {

    private String category;
    final StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mDataReference;
    private ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);
        category = getIntent().getStringExtra("productCateg");
        //imgView = (ImageView)findViewById(R.id.bo);
        //StorageReference filepath = mStorage.child("category").child(category).child("photos");
        final ArrayList<Product> products = new ArrayList<>();
        mDataReference = FirebaseDatabase.getInstance().getReference("sales").child("category").child(category);
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Product upload = snapshot.getValue(Product.class);
                    products.add(upload);
                    //Glide.with(getApplicationContext()).load(upload.getImgUrl()).into(imgView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ProductAdapter productAdapter = new ProductAdapter(BuyDetail.this,products);
        ListView listView = findViewById(R.id.list_view_items);
        listView.setAdapter(productAdapter);
    }

}
