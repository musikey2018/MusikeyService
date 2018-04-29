package com.victorsquarecity.app.friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.FriendActivityWebService;
import com.victorsquarecity.app.utils.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilaw on 26/8/2017.
 */

public class SearchFriendFragment extends Fragment {
    private static final String TAG = "SearchFriendFragment";
    private View mView = null;

    private static Context mCtx;
    List<UserModel> searchlist= new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_friend, container, false);
        Log.d(TAG, "onCreateView() creating SearchFriendFragment view");

        mCtx = getActivity().getApplicationContext();




        final EditText searchView = (EditText) mView.findViewById(R.id.search_friend_bar);
        Button searchButton = (Button) mView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = searchView.getText().toString();
                Log.d(TAG, "onCreateView() search button is clicked for  looking new friend: " + query);
//                LocationWebService.getInstance(mCtx).refreshLocations(query, VictorConstants.RESTURANT,mCtx);
                FriendActivityWebService.getInstance(mCtx).searchusers(mCtx,query);

                searchlist = FriendActivityWebService.getInstance(mCtx).getSearchuserList();
                Log.d(TAG, "billy onCreateView() array size"+searchlist.size());
                //FriendActivityWebService.getInstance(mCtx).searchusers(query,mCtx);
                //Log.d(TAG, "billy onCreateView() array size"+FriendActivityWebService.getInstance(mCtx).getSearchuserList().size());
                searchView.setText("");
                setupRecyclerView(true);
            }
        });

        setupRecyclerView(false);
        Log.d(TAG, "onCreateView() created searched user email list view");


        return mView;
    }


    public void setupRecyclerView(boolean waitforreq) {

        try {
            if (waitforreq)
            { Thread.sleep(3000);
                Log.d(TAG, "creating searched view");}


        searchlist = FriendActivityWebService.getInstance(mCtx).getSearchuserList();
        Log.d(TAG, "creating searched view"+searchlist.size());

      /*  RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.searchuUserResults);

        FriendListAdapter adapter = new FriendListAdapter(searchlist, getActivity().getApplication());
        //adapter.clearAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean onBackPressed() {
        return false;
    }

}
