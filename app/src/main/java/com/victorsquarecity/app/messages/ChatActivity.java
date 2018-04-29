package com.victorsquarecity.app.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.BottomNavHelper;

import java.util.ArrayList;

/**
 * Created by app on 6/8/2017.
 */

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private Context mCtx = ChatActivity.this;
    private static int ACTIVITY_NUM =2;

    private EditText etMessage;
    private Button btSend;
    private RecyclerView rvChat;
    private ChatAdapter chatRecyclerAdapter;
    private ArrayList<Message> mMessages;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private static String sUserId = "Bilawal";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            mMessages =fillMessageList();
        setContentView(R.layout.activity_chat);
        setupBottomNavigation();

        setupChatAdapter();

        rvChat = (RecyclerView) findViewById(R.id.rvChatview);
        btSend = (Button) findViewById(R.id.btSend);
        etMessage = (EditText) findViewById(R.id.etMessage);
        // When send button is clicked, create message object
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                Message message = new Message();
                message.setText(data);
                message.setFrined_id(sUserId);

                message.setId( String.valueOf(mMessages.size()+1));
                mMessages.add(message);

                chatRecyclerAdapter.updateList(mMessages);
                rvChat.smoothScrollToPosition(mMessages.size());

                Toast.makeText(mCtx, "Successfully sent message",
                        Toast.LENGTH_SHORT).show();
                etMessage.setText("");

                chatRecyclerAdapter.notifyDataSetChanged();
            }
        });

        rvChat.setAdapter(chatRecyclerAdapter);
    }

    /*
 add bottom navmenu
 * */
    private void setupBottomNavigation(){
        Log.d(TAG,"setting bottom navmenu");
        BottomNavigationViewEx navigationViewEx =(BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavHelper.setBottomNavView(navigationViewEx);
        BottomNavHelper.enableNavigation(mCtx,navigationViewEx);
        Menu menu = navigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void setupChatAdapter(){


        // initialize the adapter
      //  mMessages = new ArrayList<Message>();
        mMessages = fillMessageList();
        chatRecyclerAdapter = new ChatAdapter(mMessages, sUserId);

        chatRecyclerAdapter.notifyDataSetChanged();

        rvChat = (RecyclerView) findViewById(R.id.rvChatview);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvChat.setLayoutManager(mLayoutManager);
        rvChat.setItemAnimator(new DefaultItemAnimator());
        // attach the adapter to the RecyclerView
        rvChat.setAdapter(chatRecyclerAdapter);

    }

    public ArrayList<Message> fillMessageList(){
        ArrayList<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setText("this is test message");
        message.setId("1");
        message.setFrined_id(sUserId);
        messages.add(message);

        message = new Message();
        message.setText("this reply test message");
        message.setId("2");
        message.setFrined_id("you");
        messages.add(message);

        message = new Message();
        message.setText("hi, aj aae nhi");
        message.setId("3");
        message.setFrined_id(sUserId);
        messages.add(message);

        message = new Message();
        message.setText("han bs thora kam tha");
        message.setId("4");
        message.setFrined_id("you");
        messages.add(message);


        message = new Message();
        message.setText("doctor k pas jana tha");
        message.setId("5");
        message.setFrined_id(sUserId);
        messages.add(message);

        message = new Message();
        message.setText("acha, sb set hai na?");
        message.setId("6");
        message.setFrined_id(sUserId);
        messages.add(message);

        message = new Message();
        message.setText("han, doctor ki appointment thi");
        message.setId("7");
        message.setFrined_id("you");
        messages.add(message);


        message = new Message();
        message.setText("acha, set hai . class mein milty hain phir");
        message.setId("8");
        message.setFrined_id(sUserId);
        messages.add(message);

        return messages;
    }
}
