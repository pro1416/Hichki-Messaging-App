package com.example.praveshsingh.chatapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.model_classes.AddFriend;
import com.example.praveshsingh.chatapp.model_classes.AddFriendResponse;
import com.example.praveshsingh.chatapp.model_classes.ResponseDatum;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class appMemberAdapter extends RecyclerView.Adapter<appMemberAdapter.MyViewHolder> {
    private Context context;
    private List<ResponseDatum> memberlist;
    private List<ResponseDatum> filteredmemberlist;
    private SharedPreferences sharedPreferences;
    private String userid;
    APIInterface apiInterface;

    public appMemberAdapter(Context context, List<ResponseDatum> memberlist) {
        this.context = context;
        this.memberlist = memberlist;
        sharedPreferences = context.getSharedPreferences("logininfo", MODE_PRIVATE);
        userid = String.valueOf(sharedPreferences.getInt("userid", 0));
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_member, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Collections.sort(memberlist);
        ResponseDatum s = memberlist.get(position);
        String name = s.getName();
        String email = s.getEmailId();
        Integer memberid = s.getMemberId();
        holder.name.setText(name);
        holder.email.setText(email);
        holder.memberid.setText(memberid.toString());

    }


    @Override
    public int getItemCount() {
        return memberlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, memberid, email;
        Button btnsendRequest;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            memberid = itemView.findViewById(R.id.txtMemberID);
            email = itemView.findViewById(R.id.txtEmailID);
            btnsendRequest = itemView.findViewById(R.id.btnsendRequest);

            btnsendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ResponseDatum r = memberlist.get(position);
                    final String friendid = String.valueOf(r.getMemberId());
                    apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
                    Call<AddFriendResponse> call = apiInterface.sendRequest(new AddFriend(userid, friendid, userid, userid, userid));
                    call.enqueue(new Callback<AddFriendResponse>() {
                        @Override
                        public void onResponse(Call<AddFriendResponse> call, Response<AddFriendResponse> response) {
                            AddFriendResponse addFriendResponse = response.body();
                            Log.d("key", "onResponse: " + friendid);
                            if (addFriendResponse.getSuccess()) {
                                FancyToast.makeText(context, " Friend Request Sent", Toast.LENGTH_SHORT, FancyToast.INFO, false).show();
                            }
                            if (!addFriendResponse.getSuccess()) {
                                FancyToast.makeText(context, "Friend Request Unsuccessful", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                            }
                        }

                        @Override
                        public void onFailure(Call<AddFriendResponse> call, Throwable t) {

                        }
                    });
                }
            });

        }
    }
}
