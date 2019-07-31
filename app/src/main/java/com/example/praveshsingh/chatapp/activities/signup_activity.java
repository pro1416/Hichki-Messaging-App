package com.example.praveshsingh.chatapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.model_classes.SignupData;
import com.example.praveshsingh.chatapp.model_classes.SignupResponse;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_activity extends AppCompatActivity {
    EditText edtname, edtaddress, edtemail, edtphone, edtusername, edtpassword, edtconfirmpassword;
    Button btnSignup;
    APIInterface apiInterface;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        edtname = findViewById(R.id.edtname);
        edtaddress = findViewById(R.id.edtaddress);
        edtemail = findViewById(R.id.edtemail);
        edtphone = findViewById(R.id.edtmobile);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        edtconfirmpassword = findViewById(R.id.edtconfirmpass);
        btnSignup = findViewById(R.id.btnSignup);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtname.getText().toString().trim();
                String address = edtaddress.getText().toString().trim();
                String email = edtemail.getText().toString().trim();
                String phone = edtphone.getText().toString().trim();
                String username = edtusername.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();
                String confirmpassword = edtconfirmpassword.getText().toString().trim();


                if (name.equals("") || email.equals("") || username.equals("") || password.equals("")) {
                    FancyToast.makeText(signup_activity.this, "Some fields are empty", Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    return;
                }
                if (!password.equals(confirmpassword)) {
                    FancyToast.makeText(signup_activity.this, "Passwords don't match!", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    return;
                }

                apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
                Call<SignupResponse> call = apiInterface.getSignup(new SignupData(name, address, email, phone, username, password));
                call.enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        SignupResponse signupResponse = response.body();
                        int responseData = signupResponse.getResponseData();
                        if (responseData == 1) {
                            FancyToast.makeText(signup_activity.this, "User Registered", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            Intent i = new Intent(signup_activity.this,login_activity.class);
                            startActivity(i);
                        }
                        if (responseData == 2) {
                            FancyToast.makeText(signup_activity.this, "Not Registered", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        Toast.makeText(signup_activity.this, "Error occured: " + t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}
