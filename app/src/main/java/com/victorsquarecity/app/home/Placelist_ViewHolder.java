package com.victorsquarecity.app.home;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.searchresults.DetailsActivity;
import com.victorsquarecity.app.utils.LocationModel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by app on 6/9/2017.
 */

public class Placelist_ViewHolder extends RecyclerView.ViewHolder implements RecyclerViewClickListener{

    private static final String TAG = "Placelist_ViewHolder";

    @BindView(R.id.cardView)
    CardView cv;

    @BindView(R.id.place_name)
    TextView place_name;

    @BindView(R.id.place_ratings)
    TextView place_ratings;

    @BindView(R.id.place_priceRange)
    TextView place_priceRange;

    @BindView(R.id.place_location)
    TextView place_location;

    @BindView(R.id.place_tumbnail)
    ImageView place_tumbnail;

    RecyclerViewClickListener itemListener;

    Placelist_ViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = getAdapterPosition();
                Log.d(TAG,"itemView.setOnClickListener() item clicked "+pos );

                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("position",pos);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
    }
}

interface RecyclerViewClickListener {
    void recyclerViewListClicked(View v, int position);
}