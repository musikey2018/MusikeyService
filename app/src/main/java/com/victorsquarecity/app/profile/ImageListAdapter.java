package com.victorsquarecity.app.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.victorsquarecity.app.R;

/**
 * Created by bi-l-l on 6/8/2017.
 */

public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private Object[] imageUrls;

    public ImageListAdapter(Context context, Object[] imageUrls) {
        super(context, R.layout.layout_center_profile, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.layout_custom_item, parent, false);
        }

        Glide.with(context).load(imageUrls[position]).into((ImageView) convertView);

        return convertView;
    }
}