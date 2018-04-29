package com.victorsquarecity.app.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.victorsquarecity.app.R;

/**
 * Created by app on 6/8/2017.
 */

public class GridAdapter extends BaseAdapter {
    private static final String TAG = "GridAdapter";
    Context mContext ;

    int images[]= {
            R.drawable.cereals,R.drawable.friedchickenblack,R.drawable.dinnerblack,
            R.drawable.coffeecup,R.drawable.mocktail,R.drawable.wineblack
    };

    public class ViewHolder{
        public ImageView imageView;
    }

    private GridItems[] items;
    private LayoutInflater mInflater;

    public GridAdapter(Context context,GridItems[] locations) {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        items = locations;
    }

    public GridItems[] getItems() {
        return items;
    }

    public void setItems(GridItems[] items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        if(items!= null){
            return items.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(items!= null && position>=0 && position<=getCount()){
            return items[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(items!= null && position>=0 && position<=getCount()){
            return items[position].id;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder  viewHolder;

        if(view ==null){
            view = mInflater.inflate(R.layout.layout_custom_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_item_image);
            view.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder)view.getTag();
        }

        GridItems gridItems = items[position];


        return null;
    }

    private void setCatImage(int position,ViewHolder viewHolder){
        viewHolder.imageView.setImageResource(images[position]);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
