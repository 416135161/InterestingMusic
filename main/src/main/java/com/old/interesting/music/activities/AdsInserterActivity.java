package com.old.interesting.music.activities;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.old.interesting.music.BuildConfig;
import com.old.interesting.music.Config;
import com.old.interesting.music.utilities.CommonUtils;

/**
 * Created by sjning
 * created on: 2019/4/30 下午3:58
 * description:
 */
public class AdsInserterActivity extends AdsBaseActivity {
    private InterstitialAd mInterstitialAd;
    protected int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAds();
    }

    private void initAds() {
        MobileAds.initialize(this, CommonUtils.getMetaData(this, "ads_id"));
        Config.PLAY_ADS_COUNT = 0;
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(BuildConfig.DEBUG ? "ca-app-pub-3940256099942544/1033173712"
                : "ca-app-pub-7203590267874431/5624153859");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }


    protected void showInsertAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            Config.PLAY_ADS_COUNT++;
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void showAd() {
        count++;
        if (count % Config.COUNT != 0) {
            return;
        }
        if (Config.PLAY_ADS_COUNT == Config.MAX_PLAY_COUNT) {
            return;
        }
        showInsertAd();

    }
}
