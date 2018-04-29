package com.victorsquarecity.app.searchresults;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.profile.ProfileActivity;
import com.victorsquarecity.app.utils.ImageHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by mujahidmasood on 19.08.17.
 */

public class ImageSliderAdapter extends RecyclerView.Adapter<Image_ViewHolder> {

    private static final String TAG = "ImageSliderAdapter";
    private List<String> images;
    private Context context;

    public ImageSliderAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public Image_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_row, parent, false);
        Image_ViewHolder holder = new Image_ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Image_ViewHolder holder, int position) {
        ImageHelper.downloadImageWithPicasso(images.get(0),context,holder.photo_reference);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

class Image_ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo_reference)
    ImageView photo_reference;

    Image_ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}