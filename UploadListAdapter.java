package com.example.bhavya.studiohandler;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<String> mUploads;

    public UploadListAdapter(Context context,ArrayList<String> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_single, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String uploadCurrent = mUploads.get(position);
        //holder.imageView.setImageURI(uploadCurrent);
      Picasso.with(mContext)
                .load(uploadCurrent)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.display_image);
        }
    }
}