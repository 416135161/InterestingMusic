package com.old.interesting.music.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.old.interesting.music.BuildConfig;
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
    final String AD_APP_ID = "ca-app-pub-3940256099942544~3347511713";
    final String AD_UNIT_ID_DEBUG = "ca-app-pub-3940256099942544/5224354917";

    final String AD_UNIT_ID = "ca-app-pub-8270196426610526/9383663090";
    protected RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAds();
        Config.PLAY_ADS_COUNT = 0;
    }

    @Override
    protected void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    private void initAds() {
        MobileAds.initialize(this, CommonUtils.getMetaData(this, "ads_id"));
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                // Load the next rewarded video ad.
                loadRewardedVideoAd();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });
        loadRewardedVideoAd();
    }

    protected void loadRewardedVideoAd() {
        if(BuildConfig.DEBUG){
            mRewardedVideoAd.loadAd(AD_UNIT_ID_DEBUG, new AdRequest.Builder().build());
        }else {
            mRewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }

    }

    public void showAd() {
        count++;
        if (count % Config.COUNT != 0) {
            return;
        }
        if(Config.PLAY_ADS_COUNT == Config.MAX_PLAY_COUNT){
            return;
        }
        showRewardAd();
    }

    private void showRewardAd(){
        if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
            Config.PLAY_ADS_COUNT++;
        }
    }


    protected int count = 1;

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
