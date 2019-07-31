package com.example.praveshsingh.chatapp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.adapter.chatListAdapter;
import com.example.praveshsingh.chatapp.model_classes.ChatList_model;
import com.example.praveshsingh.chatapp.model_classes.FriendDetail;
import com.example.praveshsingh.chatapp.model_classes.FriendList;
import com.example.praveshsingh.chatapp.model_classes.RecievedMessage;
import com.example.praveshsingh.chatapp.model_classes.RecievedMessageList;
import com.example.praveshsingh.chatapp.model_classes.SentMessage;
import com.example.praveshsingh.chatapp.model_classes.SentMessageList;
import com.example.praveshsingh.chatapp.model_classes.UserInfotoRecieveMessage;
import com.example.praveshsingh.chatapp.model_classes.UserInfotoSendMessage;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dashboard extends Activity {
    Toolbar toolbar;
    TextView name;
    FloatingActionButton fab;
    RecyclerView chatrecycler;
    RecyclerView.LayoutManager layoutManager;
    APIInterface apiInterface;
    List<ChatList_model> recentChatList;
    HashSet<Integer> idSet = new HashSet<>();
    HashMap<Integer, String> friendMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        loadchats();
    }

    public void openmemberlist(View view) {
        Intent i = new Intent(dashboard.this, member_list_activity.class);
        startActivity(i);
    }

    public void openfriendrequests(View view) {
        Intent i = new Intent(dashboard.this, Friend_RequestList_activity.class);
        startActivity(i);
    }

    public void logout(View view) {


        new AlertDialog.Builder(dashboard.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(dashboard.this, login_activity.class);
                        startActivity(i);
                        SharedPreferences sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                    }
                })
                .setNegativeButton("Cancel", null).show();

    }

    public void loadchats() {
        toolbar = findViewById(R.id.toolbardash);
        name = findViewById(R.id.name);
        fab = findViewById(R.id.fab);
        chatrecycler = findViewById(R.id.chatlist);
        layoutManager = new LinearLayoutManager(this);
        chatrecycler.setLayoutManager(layoutManager);
        recentChatList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        final String userid = String.valueOf(sharedPreferences.getInt("userid", 0));
        final String username = sharedPreferences.getString("username", null);
        apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
        Call<FriendList> call = apiInterface.getFriendList(userid);
        call.enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                FriendList friendList = response.body();
                final List<FriendDetail> friendDetailList = friendList.getResponseData();

                for (FriendDetail f : friendDetailList) {
                    friendMap.put(f.getFriendId(), f.getFriendName());
                }

                for (int j = 0; j < friendDetailList.size(); j++) {
                    final FriendDetail i = friendDetailList.get(j);
                    final Integer friendid = i.getFriendId();

                    Call<SentMessageList> callsent = apiInterface.getSentMessage(new UserInfotoSendMessage(userid, i.getFriendId().toString()));
                    callsent.enqueue(new Callback<SentMessageList>() {
                        @Override
                        public void onResponse(Call<SentMessageList> call, Response<SentMessageList> response) {
                            SentMessageList sentMessages = response.body();
                            List<SentMessage> sentMessageList = sentMessages.getResponseData();
                            if (sentMessageList.size() != 0) {
                                idSet.add(i.getFriendId());
                            }
                        }

                        @Override
                        public void onFailure(Call<SentMessageList> call, Throwable t) {
                            Toast.makeText(dashboard.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });


                    Call<RecievedMessageList> callsent2 = apiInterface.getRecievedMessage(new UserInfotoRecieveMessage(i.getFriendId().toString(), userid));
                    final int finalJ = j;
                    callsent2.enqueue(new Callback<RecievedMessageList>() {
                        @Override
                        public void onResponse(Call<RecievedMessageList> call, Response<RecievedMessageList> response) {
                            RecievedMessageList sentMessages = response.body();
                            List<RecievedMessage> sentMessageList = sentMessages.getResponseData();
                            if (sentMessageList.size() != 0) {
                                idSet.add(i.getFriendId());

                                if (finalJ == (friendDetailList.size() - 1)) {
                                    for (Integer x : idSet) {
                                        recentChatList.add(new ChatList_model(x, friendMap.get(x)));
                                    }

                                    chatListAdapter chatListAdapter = new chatListAdapter(getApplicationContext(), recentChatList);
                                    chatrecycler.setAdapter(chatListAdapter);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RecievedMessageList> call, Throwable t) {
                            Toast.makeText(dashboard.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(dashboard.this, "Hi " + username + " , your ID is " + userid, Toast.LENGTH_LONG).show();
                    }
                });


                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(dashboard.this, FriendList_activity.class);
                        startActivity(i);
                    }
                });

            }

            @Override
            public void onFailure(Call<FriendList> call, Throwable t) {

            }


        });

    }

    public void refreshchats(View view) {
        loadchats();
    }
}