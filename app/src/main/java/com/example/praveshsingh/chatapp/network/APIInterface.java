package com.example.praveshsingh.chatapp.network;

import com.example.praveshsingh.chatapp.model_classes.ActionOnRequest;
import com.example.praveshsingh.chatapp.model_classes.ActionOnRequestResponse;
import com.example.praveshsingh.chatapp.model_classes.AddFriend;
import com.example.praveshsingh.chatapp.model_classes.AddFriendResponse;
import com.example.praveshsingh.chatapp.model_classes.ApplicationMemberList;
import com.example.praveshsingh.chatapp.model_classes.FriendList;
import com.example.praveshsingh.chatapp.model_classes.FriendRequestList;
import com.example.praveshsingh.chatapp.model_classes.LoginData;
import com.example.praveshsingh.chatapp.model_classes.LoginResponse;
import com.example.praveshsingh.chatapp.model_classes.RecievedMessageList;
import com.example.praveshsingh.chatapp.model_classes.SentMessageList;
import com.example.praveshsingh.chatapp.model_classes.SignupData;
import com.example.praveshsingh.chatapp.model_classes.SignupResponse;
import com.example.praveshsingh.chatapp.model_classes.SubmitMessage;
import com.example.praveshsingh.chatapp.model_classes.SubmitMessageResponse;
import com.example.praveshsingh.chatapp.model_classes.UserInfotoRecieveMessage;
import com.example.praveshsingh.chatapp.model_classes.UserInfotoSendMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("AccountAPI/GetLoginUser")
    Call<LoginResponse> getLogin(@Body LoginData logindata);
    @POST("AccountAPI/SaveApplicationUser")
    Call<SignupResponse> getSignup(@Body SignupData signupData);
    @GET("ApplicationFriendAPI/GetApplicationMemberList")
    Call<ApplicationMemberList> getList();
    @POST("ApplicationFriendAPI/AddFriendRequest")
    Call<AddFriendResponse> sendRequest(@Body AddFriend addFriend);
    @GET("ApplicationFriendAPI/MyFriendRequest/{MemberId}")
    Call<FriendRequestList> getFriendRequests(@Path("MemberId") String memberid);
    @GET("ApplicationFriendAPI/MyFriendList/{MemberId}")
    Call<FriendList> getFriendList(@Path("MemberId") String memberid);
    @POST("ApplicationFriendAPI/ActionOnFriendRequest")
    Call<ActionOnRequestResponse> getActionofRequest(@Body ActionOnRequest actionOnRequest);
    @POST("MessageAPI/SubmitMessage")
    Call<SubmitMessageResponse> sendMessage(@Body SubmitMessage message);
    @POST("MessageAPI/GetMyReciviedMessage")
    Call<RecievedMessageList> getRecievedMessage(@Body UserInfotoRecieveMessage userinfo);
    @POST("MessageAPI/GetMySentMessage")
    Call<SentMessageList> getSentMessage(@Body UserInfotoSendMessage userinfo);


}