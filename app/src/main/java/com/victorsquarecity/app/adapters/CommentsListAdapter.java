package com.victorsquarecity.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.ImageHelper;
import com.victorsquarecity.app.utils.pojo.LocationReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mujahidmasood on 19.08.17.
 */

public class CommentsListAdapter extends RecyclerView.Adapter<Comment_ViewHolder> {

    private static final String TAG = "CommentsListAdapter";
    private List<LocationReview> comments;
    private Context context;

    public CommentsListAdapter(Context context, List<LocationReview> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public Comment_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_item, parent, false);
        Comment_ViewHolder holder = new Comment_ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Comment_ViewHolder holder, int position) {
        LocationReview locationReview = comments.get(position);

        ImageHelper.downloadImageWithPicasso(locationReview.getAuthor_url(),context,holder.author_url);
        holder.reviwe_text.setText(locationReview.getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}

class Comment_ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.author_url)
    ImageView author_url;

    @BindView(R.id.reviwe_text)
    TextView reviwe_text;

    Comment_ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}