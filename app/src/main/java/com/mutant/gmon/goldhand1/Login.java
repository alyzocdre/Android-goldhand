package com.mutant.gmon.goldhand1;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;

public class Login extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private int attempt = 10;
    private Button login;
    private Button signin;
    //private ProgressDialog mProgressDialog;

    final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference acc = db.child("Accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       // mProgressDialog = new ProgressDialog(this);
        name = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        info = (TextView) findViewById(R.id.textView);
        login = (Button) findViewById(R.id.btnLogin);
        signin = (Button) findViewById(R.id.sign_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exists(name.getText().toString(), password.getText().toString());
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Info.class);
                startActivity(intent);
            }
        });
    }

    private void exists(final String user, final String pass) {
        //mProgressDialog.setMessage("Logging In");
        final boolean[] authenticated = {false};
        Query query = acc.orderByChild("username").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    UID user = child.getValue(UID.class);
                    if (user.getPassword1().equals(pass)) {
                        authenticated[0] = true;
                        attempt = 10;
                        Log.v("Found 1", "authenticated[0] " + authenticated[0]);
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.putExtra("contact",user.getMobile());
                        //mProgressDialog.dismiss();
                        startActivity(intent);
                        return;
                    }
                }
               // mProgressDialog.dismiss();

                attempt--;
                //info.setText(message);
                info.setText("Invalid UserID or Password");
                info.setTextColor(Color.RED);
                if (attempt == 0) {
                    login.setEnabled(false);
                    String message = "Maximum Limit Exceeded";
                    info.setText(message);
                    info.setTextColor(Color.RED);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.v("Found Again 4", "authenticated[0] " + authenticated[0]);

    }
}
