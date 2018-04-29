package com.victorsquarecity.app.friends;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.searchresults.DetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by app on 6/9/2017.
 */

public class Friendlist_ViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.username)
    TextView user_username;

    Friendlist_ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}