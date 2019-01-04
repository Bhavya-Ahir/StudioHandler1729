package com.example.bhavya.studiohandler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Gallery> mUploads;

    public AlbumListAdapter(Context context, List<Gallery> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public AlbumListAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.album_item, parent, false);
        return new AlbumListAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumListAdapter.ImageViewHolder holder, final int position) {
        Log.d("Inadapter_the_size ",mUploads.get(position).getHeader()+"  "+mUploads.get(position).getList_of_URI().size());
        final String album_title=mUploads.get(position).getHeader();
        String album_image = mUploads.get(position).getList_of_URI().get(0);
        holder.textView.setText(album_title);
        Picasso.with(mContext)
                .load(album_image)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, imagesActivity.class);
                intent.putExtra("album_title",album_title);
                intent.putExtra("album_image_list",mUploads.get(position).getList_of_URI());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView  textView;
        public ImageView imageView;
        CardView parentLayout;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.album_title);
            imageView = itemView.findViewById(R.id.album_image);
            parentLayout = itemView.findViewById(R.id.cardview_id);
        }
    }
}
