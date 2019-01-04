package com.example.bhavya.studiohandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AlbumListAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private ArrayList<String> mList_of_images;// this is needed for the check
    private ArrayList<String> mList_of_titles;// this is needed for the check
    private ArrayList<Gallery> gallery_list;// list for gallery objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mRecyclerView = findViewById(R.id.recycler_view_album);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mList_of_images = new ArrayList<String>();
        mList_of_titles = new ArrayList<String>();
        gallery_list=new ArrayList<Gallery>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("album");
        Log.i("Firebase", "outside the loop");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            int counter=0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String Title_album = postSnapshot.getKey();
                    Log.i("Firebase", "inside 1st loop and the album title is --->" + Title_album);
                    if (mList_of_titles.contains(Title_album)) {
                       // mList_of_images.clear();
                        continue;
                    }
                    else{
                        mList_of_titles.add(Title_album);
                        for (DataSnapshot inner_Snapshot : dataSnapshot.child(Title_album).getChildren()) {
                            String mUri = inner_Snapshot.getValue(String.class);
                            //Log.i("UriFirebase", mUri);
                            mList_of_images.add(mUri);

                            Log.i("Firebase", "inside 2nd loop and Image from" + Title_album);
                       }
                        /*if(mList_of_images.isEmpty())
                            Log.d("Check_condition----->","size for "+Title_album+" is "+mList_of_images.size());
                        else
                            Log.d("Check_condition----->","false");*/


                        Log.d("Check before insert->","size for "+Title_album+" is "+mList_of_images.size()+"counter "+counter);
                        if(counter!=0) {
                            for (int i = 0; i < counter; i++) {
                                mList_of_images.remove(mList_of_images.get(i));
                                Log.d("Check removed ","is "+i);
                            }
                        }
                        counter=mList_of_images.size();
                        Log.d("Check after insert->","size for "+Title_album+" is "+mList_of_images.size()+"counter"+counter);
                        gallery_list.add(new Gallery(Title_album,mList_of_images));
                        Log.d("Check size @ .add ",mList_of_images.size()+" ");
                    }
                   // mList_of_images.clear();
                }
                //Log.i("Firebase", "after data change ");

                mAdapter = new AlbumListAdapter(AlbumActivity.this, gallery_list);
                mRecyclerView.setAdapter(mAdapter);
                Log.i("Firebase", "after data change ");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AlbumActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }
}
