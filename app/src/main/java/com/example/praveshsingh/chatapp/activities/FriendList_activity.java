package com.example.praveshsingh.chatapp.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.adapter.FriendAdapter;
import com.example.praveshsingh.chatapp.adapter.FriendRequestAdapter;
import com.example.praveshsingh.chatapp.model_classes.FriendDetail;
import com.example.praveshsingh.chatapp.model_classes.FriendList;
import com.example.praveshsingh.chatapp.model_classes.FriendRequestList;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendList_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    APIInterface apiInterface;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_activity);
        recyclerView = findViewById(R.id.friendlistview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading . .");
        progressDialog.show();

        sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        String memberid = String.valueOf(sharedPreferences.getInt("userid", 0));

        apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
        Call<FriendList> call = apiInterface.getFriendList(memberid);
        call.enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                progressDialog.dismiss();
                FriendList friendList = response.body();
                List<FriendDetail> friendDetailList = friendList.getResponseData();
                FriendAdapter adapter = new FriendAdapter(FriendList_activity.this, friendDetailList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FriendList> call, Throwable t) {
                Toast.makeText(FriendList_activity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
