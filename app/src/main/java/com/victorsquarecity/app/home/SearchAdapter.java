package com.victorsquarecity.app.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.searchresults.DetailsActivity;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.ImageHelper;
import com.victorsquarecity.app.utils.LocationModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by app on 6/9/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<Placelist_ViewHolder>{

    public static final String TAG = "SearchAdapter";
    List<LocationModel> list;
    Context context;
    RecyclerViewClickListener itemListener;


    public SearchAdapter(List<LocationModel> list,Context context, RecyclerViewClickListener itemListener) {
        this.list = list;
        this.context = context;
        this.itemListener = itemListener;

    }

    @Override
    public Placelist_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_location_item, parent, false);
        Placelist_ViewHolder holder = new Placelist_ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final Placelist_ViewHolder holder, int position) {

        Log.d(TAG,"onBindViewHolder() position "+position);

        LocationModel location = list.get(position);
        Log.d(TAG, "onBindViewHolder() location "+location.toString());

        holder.place_name.setText(location.getLocationName());
        holder.place_ratings.setText(location.getLocationRatings());
        holder.place_location.setText(location.getLocationAddress());
        holder.place_priceRange.setText(location.getPriceRange());
        ImageHelper.downloadImageWithPicasso(location.getIcon(),context,holder.place_tumbnail);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, LocationModel place) {
        list.add(position, place);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified place object
    public void remove(LocationModel place) {
        int position = list.indexOf(place);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();
    }

}

