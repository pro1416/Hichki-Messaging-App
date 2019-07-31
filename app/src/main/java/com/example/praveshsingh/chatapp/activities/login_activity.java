package com.example.praveshsingh.chatapp.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.model_classes.LoginData;
import com.example.praveshsingh.chatapp.model_classes.LoginResponse;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_activity extends Activity {
    EditText edtusername, edtpassword;
    TextView appname;
    Button btnLogin;
    APIInterface apiinterface;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        edtusername = findViewById(R.id.edtusername);
        appname = findViewById(R.id.app_name);
        edtpassword = findViewById(R.id.edtpassword);
        btnLogin = findViewById(R.id.btnLogin);


        SharedPreferences sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edtusername.getText().toString().trim();
                final String password = edtpassword.getText().toString().trim();
                if (username.equals("")) {
                    edtusername.requestFocus();
                    edtusername.setError("Username required!");
                    Animation shake = AnimationUtils.loadAnimation(login_activity.this, R.anim.shake);
                    edtusername.startAnimation(shake);
                    return;
                } else if (password.equals("")) {
                    edtpassword.requestFocus();
                    edtpassword.setError("Password required!");

                    Animation shake = AnimationUtils.loadAnimation(login_activity.this, R.anim.shake);
                    edtpassword.startAnimation(shake);
                    return;
                }
                progressDialog = new ProgressDialog(login_activity.this);
                progressDialog.show();
                progressDialog.setMessage("Authenticating..");
                progressDialog.setCancelable(false);

                apiinterface = APIClient.getRetroInstance().create(APIInterface.class);
                Call<LoginResponse> call = apiinterface.getLogin(new LoginData(username, password));
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        progressDialog.dismiss();
                        LoginResponse loginResponse = response.body();
                        String errormessage = loginResponse.getErrorMessage();

                        if (errormessage.equals("User Authenticated!!")) {
                            FancyToast.makeText(login_activity.this, "User Aunthenticated", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.putInt("userid", loginResponse.getApplicationUserId());
                            editor.apply();
                            Intent intent = new Intent(login_activity.this, dashboard.class);
                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(login_activity.this, appname, "sharedTextView");
                            startActivity(intent, activityOptions.toBundle());
                            finish();
                        }
                        if (errormessage.equals("Invalid username!!")) {
                            FancyToast.makeText(login_activity.this, "Invalid Username", Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        }
                        if (errormessage.equals("Invalid password!!")) {
                            FancyToast.makeText(login_activity.this, "Invalid Password", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(login_activity.this, "Error occured: " + t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

    public void signup(View view) {
        Intent i = new Intent(login_activity.this, signup_activity.class);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(login_activity.this, appname, "sharedTextView");
        startActivity(i, activityOptions.toBundle());
    }

}
