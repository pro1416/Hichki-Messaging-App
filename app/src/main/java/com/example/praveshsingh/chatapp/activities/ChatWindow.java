package com.example.praveshsingh.chatapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.adapter.chatAdapter;
import com.example.praveshsingh.chatapp.model_classes.Messagez;
import com.example.praveshsingh.chatapp.model_classes.RecievedMessage;
import com.example.praveshsingh.chatapp.model_classes.RecievedMessageList;
import com.example.praveshsingh.chatapp.model_classes.SentMessage;
import com.example.praveshsingh.chatapp.model_classes.SentMessageList;
import com.example.praveshsingh.chatapp.model_classes.SubmitMessage;
import com.example.praveshsingh.chatapp.model_classes.SubmitMessageResponse;
import com.example.praveshsingh.chatapp.model_classes.UserInfotoRecieveMessage;
import com.example.praveshsingh.chatapp.model_classes.UserInfotoSendMessage;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatWindow extends AppCompatActivity {
    String friendName, friendId;
    EditText edtMessage;
    TextView txtViewName, ID, dp;
    Button btnSend;
    RecyclerView messageRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    APIInterface apiInterface;
    SharedPreferences sharedPreferences;
    String userid;
    List<Messagez> messageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatwindow_activity);
        mainwork();


    }

    public void mainwork() {
        edtMessage = findViewById(R.id.edtMessage);
        txtViewName = findViewById(R.id.txtViewName);
        ID = findViewById(R.id.ID);
        dp = findViewById(R.id.dp);
        btnSend = findViewById(R.id.btnSend);
        messageRecyclerView = findViewById(R.id.chatrecycler);
        layoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(layoutManager);
        messageList = new ArrayList<>();


        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            friendName = bundle.getString("friendname");
            friendId = bundle.getString("friendid");
        }
        txtViewName.setText(friendName);
        ID.setText(friendId);
        dp.setText(friendName.substring(0, 1).toUpperCase());

        sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        userid = String.valueOf(sharedPreferences.getInt("userid", 0));
        edtMessage.requestFocus();

        apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
        Call<RecievedMessageList> call = apiInterface.getRecievedMessage(new UserInfotoRecieveMessage(friendId, userid));
        call.enqueue(new Callback<RecievedMessageList>() {
            @Override
            public void onResponse(Call<RecievedMessageList> call, Response<RecievedMessageList> response) {
                RecievedMessageList recievedMessage = response.body();
                List<RecievedMessage> recievedMessageList = recievedMessage.getResponseData();
                for (int i = 0; i < recievedMessageList.size(); i++) {
                    RecievedMessage r = recievedMessageList.get(i);
                    messageList.add(new Messagez(r.getMessage(), Messagez.MSG_TYPE_RECIEVED, r.getCreatedDate()));
                    Log.d("msglistsize", "onResponse: " + messageList.toString());
                }
            }

            @Override
            public void onFailure(Call<RecievedMessageList> call, Throwable t) {

            }
        });


        Call<SentMessageList> callsent = apiInterface.getSentMessage(new UserInfotoSendMessage(userid, friendId));
        callsent.enqueue(new Callback<SentMessageList>() {
            @Override
            public void onResponse(Call<SentMessageList> call, Response<SentMessageList> response) {
                SentMessageList sentMessages = response.body();
                List<SentMessage> sentMessageList = sentMessages.getResponseData();
                for (int i = 0; i < sentMessageList.size(); i++) {
                    SentMessage s = sentMessageList.get(i);
                    messageList.add(new Messagez(s.getMessageBody(), Messagez.MSG_TYPE_SENT, s.getCreatedDate()));
                }
            }

            @Override
            public void onFailure(Call<SentMessageList> call, Throwable t) {

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtMessage.getText().toString().trim();
                edtMessage.setText("");
                edtMessage.requestFocus();
                if (message.equals("")) {
                    return;
                } else {

                    Call<SubmitMessageResponse> call = apiInterface.sendMessage(new SubmitMessage(userid, "jalwa", message, friendId));
                    call.enqueue(new Callback<SubmitMessageResponse>() {
                        @Override
                        public void onResponse(Call<SubmitMessageResponse> call, Response<SubmitMessageResponse> response) {
                            SubmitMessageResponse submitMessageResponse = response.body();
                            if (submitMessageResponse.getSuccess()) {
                                Toast.makeText(ChatWindow.this, "Message Sent", Toast.LENGTH_SHORT).show();
                            }
                            if (!submitMessageResponse.getSuccess()) {
                                Toast.makeText(ChatWindow.this, "Message NOT Sent!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SubmitMessageResponse> call, Throwable t) {
                            Toast.makeText(ChatWindow.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
        Collections.sort(messageList);
        chatAdapter chatAdapter = new chatAdapter(ChatWindow.this, messageList);
        messageRecyclerView.setAdapter(chatAdapter);
    }

    public void refresh(View view) {
        mainwork();
    }
}
