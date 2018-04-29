package com.victorsquarecity.app.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.victorsquarecity.app.R;

/**
 * Created by app on 6/8/2017.
 */

public class GridFragment extends Fragment{

    private GridView mGridView;
    private GridAdapter gridAdapter;
    GridItems[] gridItems ={};
    private Activity activity;



    public GridFragment(GridItems[] gridItems, Activity activity) {
        this.gridItems = gridItems;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_grid_main_center,container,false);
        mGridView = (GridView)view.findViewById(R.id.gridview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(activity!= null){
            gridAdapter = new GridAdapter(activity,gridItems);

            if(mGridView != null){
                mGridView.setAdapter(gridAdapter);
            }

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onGridItemCLick((GridView)parent,view,position,id);
                }
            });

        }


    }

    public void onGridItemCLick(GridView gridView,View view,int position, long id){
        Toast.makeText(activity,"position clicked "+position,Toast.LENGTH_LONG);
    }
}
