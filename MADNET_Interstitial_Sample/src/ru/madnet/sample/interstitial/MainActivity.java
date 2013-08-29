package ru.madnet.sample.interstitial;


import android.app.Activity;
import android.os.Bundle;

import com.example.madnetinterstitialsample.R;
import com.mad.ad.AdInterstitialView;
import com.mad.ad.AdInterstitialView.AdInterstitialListener;
import com.mad.ad.AdRequest;
import com.mad.ad.AdResponseStatus;

public class MainActivity extends Activity implements AdInterstitialListener{

    //ID для MADNET SDK, выдается на сайте MADNET
    //ЗДЕСЬ ДОЛЖЕН БЫТЬ ВАШ MADNET ID
    private static final String MADNET_SPACE_ID = "2";
    
    //В данном примере будет использоваться тестовый режим
    private static final boolean TEST_MODE = true;
    private static final boolean DEBUG_MODE = TEST_MODE;
    
    private AdInterstitialView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = new AdInterstitialView(this, MADNET_SPACE_ID, TEST_MODE, DEBUG_MODE);
        mView.loadBanner(new AdRequest.Builder().getRequest());
    }

    @Override
    public void onReady() {
        mView.showBanner();
    }

    @Override
    public void onGetResponse(AdResponseStatus arg0) {
        // Если вы хотите каким-то образом обработать результат ответа сервера,
        // то сделать это можно здесь
    }

    
}
