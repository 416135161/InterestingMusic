package com.old.interesting.music.activities;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.old.interesting.music.BuildConfig;
import com.old.interesting.music.Config;
import com.old.interesting.music.utilities.CommonUtils;

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
        if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    private int count = 5;

}
