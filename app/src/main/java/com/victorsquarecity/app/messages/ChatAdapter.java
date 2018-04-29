package com.victorsquarecity.app.messages;

/**
 * Created by bilaw on 6/8/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.victorsquarecity.app.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageItemViewHolder>
{

    private List<Message> messageList;
    private String mUserId;

    public ChatAdapter(final List<Message> messages, final String userId)
    {
        this.messageList = messages;
        this.mUserId = userId;
    }
    @Override
    public MessageItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.layout_chat_item, viewGroup, false);
        return new MessageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageItemViewHolder holder, int position) {
        Message message = messageList.get(position);

        final boolean isMe = message.getFrined_id().equals(mUserId);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        final ImageView profileView = isMe ? holder.imageRight : holder.imageLeft;

        profileView.setImageResource(isMe ?R.drawable.coffeecup : R.drawable.wineblack);
        holder.body.setText(message.getText());
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }


    @Override
    public int getItemCount() {
        return this.messageList.size();
    }

    public class MessageItemViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;

        public MessageItemViewHolder(View itemView)
        {
            super(itemView);
            imageLeft = (ImageView) itemView.findViewById(R.id.ivProfileLeft);
            imageRight = (ImageView) itemView.findViewById(R.id.ivProfileRight);
            body = (TextView) itemView.findViewById(R.id.tvBody);
        }

    }

    // This method is used to update data for adapter and notify adapter that data has changed
    public void updateList(List<Message> data) {
        messageList = data;
        notifyDataSetChanged();
    }

    public void addMessage(Message message)
    {
        messageList.add(message);
        notifyItemInserted(messageList.size()-1);
    }
}
