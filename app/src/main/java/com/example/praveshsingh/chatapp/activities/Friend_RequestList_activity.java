package com.example.praveshsingh.chatapp.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.adapter.FriendRequestAdapter;
import com.example.praveshsingh.chatapp.model_classes.FriendRequestList;
import com.example.praveshsingh.chatapp.model_classes.RequestDetail;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Friend_RequestList_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    APIInterface apiInterface;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    TextView norequests;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request_list);
        recyclerView = findViewById(R.id.requestlistview);
        linearLayout = findViewById(R.id.linearLayout);
        norequests = findViewById(R.id.norequests);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        String memberid = String.valueOf(sharedPreferences.getInt("userid", 0));
        norequests.setVisibility(View.GONE);
        apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
        Call<FriendRequestList> call = apiInterface.getFriendRequests(memberid);
        call.enqueue(new Callback<FriendRequestList>() {
            @Override
            public void onResponse(Call<FriendRequestList> call, Response<FriendRequestList> response) {
                progressDialog.dismiss();
                FriendRequestList friendRequestList = response.body();
                List<RequestDetail> requestDetailList = friendRequestList.getResponseData();
                Log.d("hello", "onResponse: " + requestDetailList.size());
                refreshrecycler(requestDetailList);
                FriendRequestAdapter adapter = new FriendRequestAdapter(Friend_RequestList_activity.this, requestDetailList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<FriendRequestList> call, Throwable t) {
                Toast.makeText(Friend_RequestList_activity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void refreshrecycler(List<RequestDetail> requestDetailList) {
        {
            if (requestDetailList.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                norequests.setVisibility(View.VISIBLE);
            }

        }
    }
}
