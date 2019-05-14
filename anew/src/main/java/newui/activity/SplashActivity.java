package newui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.newui.interesting.music.R;


import com.old.interesting.music.Config;
import com.old.interesting.music.activities.HomeActivity;

import newui.animations.PulseAnimation;

public class SplashActivity extends AppCompatActivity {


    int PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Config.tf4 = Typeface.createFromAsset(getAssets(), "fonts/Intro_Cond_Light.otf");
            Config.tf3 = Typeface.createFromAsset(getAssets(), "fonts/Gidole-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
        } else {
            startHomeActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean allGranted = true;

        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
        }
        if (allGranted) {
            startHomeActivity();
        } else {
            Toast.makeText(this, "Please grant the requested permissions.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void startHomeActivity() {
        PulseAnimation.create().with(findViewById(R.id.splash_img))
                .setDuration(300)
                .setRepeatCount(4)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 1500);
    }

    public void requestPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
    }
}