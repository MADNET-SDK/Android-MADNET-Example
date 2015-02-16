package ru.madnet.sample.adview;

import android.os.Bundle;
import android.app.Activity;
import com.madnet.ads.AdRequest;
import com.madnet.ads.AdStaticView;

public class MainActivity extends Activity {

    private AdStaticView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FIXME - Look into XML (activity_main) to details!
        mAdView = (AdStaticView) findViewById(R.id.adView);
    }

    /*
     You should notify mAdView about lifecycle events to prevent
     memory leaks ant unnecessary requests to server.
     */   
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
