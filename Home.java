package com.example.bhavya.studiohandler;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {
    private static  final int RESULT_LOAD_IMAGE=1;
    private Button mselectbtn;
    private UploadListAdapter mUploadListAdapter;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    ArrayList<Uri> imageList=new ArrayList<Uri>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mselectbtn=(Button)findViewById(R.id.upload_button);
         mUploadListAdapter=new UploadListAdapter(this,imageList);

        RecyclerView mlistView = (RecyclerView) findViewById(R.id.recycler_view);
        mlistView.setLayoutManager(new LinearLayoutManager(this));
        mlistView.setHasFixedSize(true);
        mlistView.setAdapter(mUploadListAdapter);

        mselectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SELECT IMAGES"),RESULT_LOAD_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK){
            if(data.getClipData()!=null){
                int totalItemSelected=data.getClipData().getItemCount();
                for(int i=0;i<totalItemSelected;i++)
                {
                    imageList.add(data.getClipData().getItemAt(i).getUri());
                    mUploadListAdapter.notifyDataSetChanged();
                }

            }
            else if(data.getData()!=null){

            }
        }
    }
}
