package com.mutant.gmon.goldhand1;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Info extends AppCompatActivity implements View.OnTouchListener {

    private Button signup;
    private EditText username;
    private EditText password1;
    private EditText password2;
    private EditText mobile;
    private EditText dob;
    private TextView info;
    private EditText et;
    //private ProgressDialog progressDialog ;
    final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference acc = db.child("Accounts");

   // String fileName = "res/raw/userdata.txt";
    TextView tmp;
    @Override
    public boolean onTouch(View v, MotionEvent arg) {
        switch (v.getId()) {
            case R.id.new_name:
                tmp =(TextView)findViewById(R.id.new_nameT);
                tmp.setText("username");

                break;
            case R.id.mobile_no:
                tmp =(TextView)findViewById(R.id.mobile_noT);
                tmp.setText("mobile");
                break;
            case R.id.passId1:
                tmp = (TextView)findViewById(R.id.passId1T);
                tmp.setText("password");
                break;
            case R.id.passId2:
                tmp=(TextView)findViewById(R.id.passId2T);
                tmp.setText("re-type");
                break;
            case R.id.dob:
                tmp=(TextView)findViewById(R.id.dobT);
                tmp.setText("dob");
                break;
        }
        tmp.setTextColor(getResources().getColor(R.color.colorAccent));
        et = (EditText)v;
        et.setHint(null);
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        username = (EditText)findViewById(R.id.new_name);
        password1 = (EditText)findViewById(R.id.passId1);
        password2 = (EditText)findViewById(R.id.passId2);
        mobile= (EditText)findViewById(R.id.mobile_no);
        dob = (EditText)findViewById(R.id.dob);
        signup=(Button)findViewById(R.id.signup_button);

        username.setOnTouchListener(this);
        mobile.setOnTouchListener(this);
        password1.setOnTouchListener(this);
        password2.setOnTouchListener(this);
        dob.setOnTouchListener(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(password1.getText().toString().equals(password2.getText().toString()))) {
                    Toast toast = Toast.makeText(getApplicationContext(), "password mismatch", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {


                    Query query = acc.orderByChild("mobile").equalTo(mobile.getText().toString().trim());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot user : dataSnapshot.getChildren()){
                                    UID uid = user.getValue(UID.class);
                                    if(uid.getUsername() != null){
                                        Toast.makeText(getApplicationContext(),"Number Already registered!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else{

                                createAcc();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                   /* boolean created = false;
                    if(created == false) {
                        createAcc() ;
                    }*/
                }
            }
        });
    }
    /*private boolean exists(){
        final boolean[] exist = {false};
        acc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query = acc.orderByChild("mobile").equalTo(mobile.getText().toString());
                if(query != null){
                    exist[0] = true;
                    Toast.makeText(getApplicationContext(),"Number Already registered!",Toast.LENGTH_SHORT).show();
                    return ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return exist[0];
    }*/

    private void createAcc() {

        final UID user = new UID(username.getText().toString().trim(),password1.getText().toString(),mobile.getText().toString().trim(),dob.getText().toString().trim());
        acc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference users = acc.child(mobile.getText().toString());
                users.setValue(user);
                Toast.makeText(getApplicationContext(),"Account Created!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Unable to reach Database",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
