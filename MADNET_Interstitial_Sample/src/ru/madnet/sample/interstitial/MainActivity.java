package ru.madnet.sample.interstitial;


import android.app.Activity;
import android.os.Bundle;
import ru.madnet.sample.interstitial.R;

import com.madnet.ads.AdInterstitialView;
import com.madnet.ads.AdInterstitialView.AdInterstitialListener;
import com.madnet.ads.AdRequest;
import com.madnet.ads.AdResponseStatus;

public class MainActivity extends Activity implements AdInterstitialListener {

    @Deprecated
    /*
    FIXME - This is MADNET ad placement identifier. You should obtain your
    own at MADNET site and replace current value by your placementID.
    Annotation only to attract attention.
    */
    private static final String MADNET_SPACE_ID = "YOUR_MADNET_ID - take it from madnet site!";
    
    //Lets try testmode in this sample
    private static final boolean TEST_MODE = true;
    
    private AdInterstitialView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = new AdInterstitialView(this, MADNET_SPACE_ID, TEST_MODE);
        mView.loadBanner(new AdRequest.Builder().getRequest());
    }

    @Override
    public void onReady() {
        /*Here you can make some prepartions before show
        loaded interstitial. It can be useful, if device have bad connection with
        internet - for example, it is too late to show banner and you can
        avoid demonstration here by using "return"
        */
        mView.showBanner();
    }

    @Override
    public void onGetResponse(AdResponseStatus response) {
        /*
        Here you can handle response from server - for example, you can do
        something, if "response == AdResponseStatus.RESPONSE_ERROR".
        */
    }

    
}
