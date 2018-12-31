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

/*public class UploadListAdapter extends ArrayAdapter<Uri> {

    public UploadListAdapter(Activity context, ArrayList<Uri> imageList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, imageList);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_single, parent, false);
        }
        Uri currentUri=getItem(position);

        ImageView mimage=(ImageView)listItemView.findViewById(R.id.display_image);
        mimage.setImageURI(currentUri);
        return listItemView;
    }
}
*/
public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Uri> mUploads;

    public UploadListAdapter(Context context, List<Uri> uploads) {
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
        Uri uploadCurrent = mUploads.get(position);
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