package com.victorsquarecity.app.friends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.db.dao.UserORM;

import java.util.List;

/**
 * Created by bilaw on 26/8/2017.
 */

public class FriendListAdapter extends RecyclerView.Adapter<Friendlist_ViewHolder> {

    public static final String TAG = "FriendListAdapter";
    List<String> list;
    Context context;
    UserORM user;


    public FriendListAdapter(Context context, UserORM user) {
        this.context = context;
        this.user = user;
        list = user.getFriends();
        TastyToast.makeText(context, "" + list.size(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
    }

    @Override
    public Friendlist_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendlist_item, parent, false);
        Friendlist_ViewHolder holder = new Friendlist_ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final Friendlist_ViewHolder holder, int position) {

        //TODO get user friend data.

        holder.user_username.setText(user.getMUserName());
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


}