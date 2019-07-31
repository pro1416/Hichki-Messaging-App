package com.example.praveshsingh.chatapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.praveshsingh.chatapp.R;

public class NoUI extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("logininfo",MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        if(username==null){
            Intent i = new Intent(NoUI.this,splashscreen.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(NoUI.this,dashboard.class);
            startActivity(i);
        }
        finish();
    }
}
