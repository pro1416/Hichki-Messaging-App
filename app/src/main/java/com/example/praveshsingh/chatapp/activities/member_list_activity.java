package com.example.praveshsingh.chatapp.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.adapter.appMemberAdapter;
import com.example.praveshsingh.chatapp.model_classes.AddFriend;
import com.example.praveshsingh.chatapp.model_classes.AddFriendResponse;
import com.example.praveshsingh.chatapp.model_classes.ApplicationMemberList;
import com.example.praveshsingh.chatapp.model_classes.ResponseDatum;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class member_list_activity extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    APIInterface apiInterface;
    List<ResponseDatum> responseDatumArrayList;
    ProgressDialog progressDialog;
    appMemberAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_member_list);
        rv = findViewById(R.id.recylerView);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading . .");
        progressDialog.show();

        apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
        Call<ApplicationMemberList> call = apiInterface.getList();
        call.enqueue(new Callback<ApplicationMemberList>() {
            @Override
            public void onResponse(Call<ApplicationMemberList> call, Response<ApplicationMemberList> response) {
                progressDialog.dismiss();
                ApplicationMemberList memberList = response.body();
                responseDatumArrayList = memberList.getResponseData();
                if (!memberList.getSuccess()) {
                    FancyToast.makeText(member_list_activity.this, "Failed to get member list", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                }

                adapter = new appMemberAdapter(member_list_activity.this, responseDatumArrayList);
                rv.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ApplicationMemberList> call, Throwable t) {
                Toast.makeText(member_list_activity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }

}
