package com.mutant.gmon.goldhand1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.master.glideimageview.GlideImageView;

import java.io.File;

public class SellDetail extends AppCompatActivity {

    //private ImageView imageView;
    private ImageButton imageButton;
    private EditText editText;
    private TextView textView;
    private Button btn;
    private String phone = null;

    private static final int CAMERA_REQUEST_CODE = 101;
    private GlideImageView glideImageView ;
    private Uri img = null;
    private String path;
    private File output=null;
    private static final int CAMERA_REQUEST=1888;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
   // final  StorageReference filepath = mStorage.child("category/education/photos");
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("sales");
    //final DatabaseReference acc = db.child("Sales");
    private ProgressDialog mProgress;
    private Intent dataDup = null;
    private Uri updateUri = null;
    private boolean proceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_detail);

        phone = getIntent().getStringExtra("contact");

        glideImageView = (GlideImageView)findViewById(R.id.productimg);
        glideImageView.setImageResource(getIntent().getIntExtra("imgCategory",0));
        imageButton = (ImageButton)findViewById(R.id.productbtn);
        editText = (EditText)findViewById(R.id.productname);
        textView = (TextView)findViewById(R.id.productctg);
        btn = (Button)findViewById(R.id.uploadbtn);
        mProgress = new ProgressDialog(this);
        textView.setText(getIntent().getStringExtra("productCateg"));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override public void onSuccess(AuthResult authResult) {
                    // do your stuff
                }
            }) .addOnFailureListener(this, new OnFailureListener() {
                @Override public void onFailure(@NonNull Exception exception) {
                    Log.e("TAG", "signInAnonymously:FAILURE", exception);
                }
            });
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent mintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File pictureDirectory= getExternalFilesDir(null);
                //glideImageView = (GlideImageView)findViewById(R.id.productimg);
                if(editText.getText().toString() != null) {
                    String pictureName = getPictureName();
                    File imagefile = new File(pictureDirectory, pictureName);
                    path = imagefile.getPath();
                    // Uri pictureUri = Uri.fromFile(imagefile);
                    Uri pictureUri = FileProvider.getUriForFile(SellDetail.this, getApplicationContext().getPackageName() + ".my.package.name.provider", imagefile);
                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pictureUri);
                    img = pictureUri;
                    updateUri = pictureUri;
                    //imageView.setImageURI(pictureUri);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    Toast.makeText(SellDetail.this, pictureUri.getPath(), Toast.LENGTH_LONG).show();
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else{
                    Toast.makeText(SellDetail.this, "Enter the name First!!", Toast.LENGTH_SHORT).show();
                }
            }
                /*if(mintent.resolveActivity(getPackageManager()) != null)  {

                    startActivityForResult(mintent, CAMERA_REQUEST_CODE);
                }*/
            });

        final Task<Uri>[] downloadUrl = new Task[2];
        final Product[] product = new Product[2];

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proceed == true) {
                    mProgress.setMessage("Uploading ...");
                    mProgress.show();
                    Uri uri = updateUri;
                   /* if(uri != null) {
                        final StorageReference filepath = mStorage.child("category").child(textView.getText().toString()).child("photos").child(getIntent().getStringExtra("contact")+editText.getText().toString().trim());
                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mProgress.dismiss();

                               // Toast.makeText(SellDetail.this, taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                                //downloadUrl[0] = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                               //  product[0] = new Product(downloadUrl[0].toString(),editText.getText().toString().trim(),phone);
                               // db.child(textView.getText().toString()).setValue(taskSnapshot.getMetadata().getReference().getDownloadUrl());

                                 filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        db.child(textView.getText().toString()).setValue(uri);
                                    }
                                });
                                Upload update = new Upload("dem",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                                db.child(textView.getText().toString()).setValue(update);

                                Toast.makeText(SellDetail.this, "Uploading Finished", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/

                    if (uri != null)
                    {
                        final StorageReference filepath = mStorage.child("category").child(textView.getText().toString()).child("photos").child(getIntent().getStringExtra("contact")+editText.getText().toString().trim());
                        filepath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                        {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                            {
                                if (!task.isSuccessful())
                                {
                                    throw task.getException();
                                }
                                return filepath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                                mProgress.dismiss();
                                if (task.isSuccessful())
                                {
                                    Uri downloadUri = task.getResult();
                                    Log.e("Tag", "then: " + downloadUri.toString());


                                    Product upload = new Product(
                                            downloadUri.toString(),editText.getText().toString().trim(),phone);
                                    db.child("category").child(textView.getText().toString().trim()).push().setValue(upload);
                                    //db.child(textView.getText().toString()).setValue(upload);
                                    Toast.makeText(SellDetail.this,"upload finished: ",Toast.LENGTH_SHORT).show();
                                } else
                                {
                                    Toast.makeText(SellDetail.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
               /* acc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DatabaseReference products = acc.child(phone);
                        products.setValue(product[0]);
                        Toast.makeText(getApplicationContext(),"DataBase Updated!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Unable to reach Database",Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ){
            proceed = true;
            if(path == null){
                Log.v("pathV","null");
            }
            glideImageView.loadImageUrl(path);

            //Toast.makeText(SellDetail.this,"Here "+ tempUri, Toast.LENGTH_LONG).show();

           // Show Uri path based on Cursor Content Resolver
            /*Toast.makeText(this, "Real path for URI : "+getRealPathFromURI(tempUri), Toast.LENGTH_SHORT).show();
                //mProgress.show();

                //StorageReference filepath = mStorage.child("category/education/photos").child("123.jpg");
                if (tempUri != null) {
                    filepath.putFile(tempUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             //downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            Toast.makeText(SellDetail.this, "Uploading Finished", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("error", "failed");
                        }
                    });
                }*/


        }
    }

    private String getPictureName(){
        return getIntent().getStringExtra("contact")+"#"+editText.getText().toString().trim()+"#";
    }


}
