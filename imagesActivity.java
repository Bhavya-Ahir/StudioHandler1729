package com.example.bhavya.studiohandler;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class imagesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UploadListAdapter mAdapter;
    private ArrayList<String> mList_of_images;// this is needed for the check

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
       // if(getIntent().hasExtra("album_title") && getIntent().hasExtra("album_image_list"));
            mRecyclerView=findViewById(R.id.recycler_view_images);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            String albumName = getIntent().getStringExtra("album_title");
            mList_of_images=new ArrayList<String>();
            mList_of_images = getIntent().getStringArrayListExtra("album_image_list");
            mAdapter = new UploadListAdapter(imagesActivity.this,mList_of_images);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


