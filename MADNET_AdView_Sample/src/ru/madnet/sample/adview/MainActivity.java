package ru.madnet.sample.adview;

import com.mad.ad.AdStaticView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    private AdStaticView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdStaticView) findViewById(R.id.adView);

    }

    @Override
    protected void onDestroy() {
        mAdView.dismiss();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mAdView.pause();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdView.resume();
    }
}
