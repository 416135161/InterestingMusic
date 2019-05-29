package com.old.interesting.music.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.old.interesting.music.Config;
import com.old.interesting.music.R;
import com.old.interesting.music.fragments.PlayerFragment.PlayerFragment;
import com.old.interesting.music.utilities.CommonUtils;
import com.old.interesting.music.utilities.SpHelper;

import newui.base.BaseActivity;

/**
 * Created by sjning
 * created on: 2019/1/24 上午10:13
 * description:
 */
public class AdsBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showStarDialog(){
        new  AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_please_rate) )
                .setView(getLayoutInflater().inflate(R.layout.dlg_star, null))
                .setPositiveButton(getString(R.string.text_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpHelper.getDefault(AdsBaseActivity.this).putBoolean(SpHelper.KEY_STAR, true);
                        jumpToPlay();
                    }
                })
                .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doFinish();
                    }
                })
                .show();
    }

    protected void doFinish(){
        if(PlayerFragment.mMediaPlayer != null && PlayerFragment.mMediaPlayer.isPlaying()){
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
        }else {
            finish();
        }
    }

    private void jumpToPlay(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Log.e("to play star: " + Thread.currentThread().getStackTrace()[2].getLineNumber(), e.getMessage());
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }


}
