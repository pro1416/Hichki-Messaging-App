package com.example.praveshsingh.chatapp.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.praveshsingh.chatapp.R;

public class splashscreen extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);
        final TextView appname = findViewById(R.id.app_name);
        Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        appname.startAnimation(fadein);
        final Thread thread = new Thread() {
            public void run() {
                super.run();
                try {
                    sleep(1700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(splashscreen.this, login_activity.class);
                           ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(splashscreen.this, appname, "sharedTextView");
                            startActivity(i, options.toBundle());
                        }
                    });

                }
            }
        };
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
