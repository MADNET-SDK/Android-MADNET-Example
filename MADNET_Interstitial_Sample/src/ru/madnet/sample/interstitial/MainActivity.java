package ru.madnet.sample.interstitial;


import android.app.Activity;
import android.os.Bundle;

import com.example.madnetinterstitialsample.R;
import com.mad.ad.AdInterstitialView;
import com.mad.ad.AdInterstitialView.AdInterstitialListener;
import com.mad.ad.AdRequest;
import com.mad.ad.AdResponseStatus;

public class MainActivity extends Activity implements AdInterstitialListener{

    private AdInterstitialView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = new AdInterstitialView(this, "2", true, true);
        mView.loadBanner(new AdRequest.Builder().getRequest());
    }

    @Override
    public void onReady() {
        mView.showBanner();
    }

    @Override
    public void onGetResponse(AdResponseStatus arg0) {
        // TODO Auto-generated method stub
    }

    
}
