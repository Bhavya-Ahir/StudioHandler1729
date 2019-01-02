package com.example.bhavya.studiohandler;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Just upload the images to firebase here
public class Home extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private Button mselectbtn;
    private UploadListAdapter mUploadListAdapter;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private EditText album_name;
    private StorageTask mUploadTask;
    private TextView mTextViewShowUploads;
    //ArrayList<Uri> imageList = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        album_name = (EditText) findViewById(R.id.album_name);
        mselectbtn = (Button) findViewById(R.id.upload_button);

        mStorageRef = FirebaseStorage.getInstance().getReference("album");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("album");
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);

        /*RecyclerView mlistView = (RecyclerView) findViewById(R.id.recycler_view);
        mlistView.setLayoutManager(new LinearLayoutManager(this));
        mlistView.setHasFixedSize(true);
        mlistView.setAdapter(mUploadListAdapter);*/

        mselectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "SELECT IMAGES"), RESULT_LOAD_IMAGE);

            }
        });
        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int totalItemSelected = data.getClipData().getItemCount();
                for (int i = 0; i < totalItemSelected; i++) {
                   // imageList.add(data.getClipData().getItemAt(i).getUri());
                    uploadFile(data.getClipData().getItemAt(i).getUri());
                   // mUploadListAdapter.notifyDataSetChanged();
                }

            } else if (data.getData() != null) {

            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile(Uri mImageUri) {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(album_name.getText().toString()+"/"+System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Handler handler = new Handler();
                            //handler.postDelayed(new Runnable() {
                              /*  @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);*/

                            Toast.makeText(Home.this, "Upload successful", Toast.LENGTH_LONG).show();
                            String upload = taskSnapshot.getDownloadUrl().toString();
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(album_name.getText().toString()).child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                   /* .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                       /* public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });*/
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImagesActivity() {
        Intent intent = new Intent(this, imagesActivity.class);
        startActivity(intent);
    }

}
