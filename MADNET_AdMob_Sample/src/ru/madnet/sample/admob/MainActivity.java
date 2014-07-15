package ru.madnet.sample.admob;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.madnet.ads.mediation.admob.AdMobMadAdapter;
import ru.madnet.sample.admobsample.R;

/**
 * WARNING! Example actual until 1 august 2014.
 * DO NOT use legacy admob, it will be replaced by Google Play Services.
 * Instead this sample look at "MADNET GooglePlayServices AdMob Sample" in Git.
 */
public class MainActivity extends Activity {

	@Deprecated
    /*
    This is label of AdMob custom event. You should specify it as a
    labled's name at AdMob site (label's name can differs from "some_fake_label",
	but MUST match with value, specified at AdMob size).
    */
    private static final String ADMOB_CUSTOM_EVENT_LABEL = "some_fake_label";
	
    @Deprecated
    /*
    FIXME - This is an AdMob mediation ID - you should take it at AdMob site.
    Annotation only to attract attention.
    */	
    private static final String ADMOB_MEDIATION_ID = "YOUR_ADMOB_MEDIATION_ID - take it from Admob site!";
    
    @Deprecated
    /*
    FIXME - This is MADNET ad placement identifier. You should obtain your
    own at MADNET site and replace current value by your placementID.
    Annotation only to attract attention.
    */
	private static final String MADNET_SPACE_ID = "YOUR_MADNET_ID - take it from madnet site!"; 
   
    private AdMobMadAdapter mAdMobMadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*Note, that Dimension.MAD_SIZE_320x50 have the same size as 
        AdMob AdSize.BANNER. To use other banner sizes, you should specify
        matching size of AdMob and MADNET for best performance*/
        AdView admobview = new AdView(this, AdSize.BANNER, ADMOB_MEDIATION_ID);
        mAdMobMadAdapter = new AdMobMadAdapter(admobview, this, AdSize.BANNER, ADMOB_CUSTOM_EVENT_LABEL, MADNET_SPACE_ID, true);        

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);        
        layout.addView(admobview);
        
        mAdMobMadAdapter.showBanners(new AdRequest()); 
    }

    /*
     You should notify adapter about lifecycle events to prevent
     memory leaks ant unnecessary requests to server.
     */    
    @Override
    protected void onDestroy() {
        if (mAdMobMadAdapter != null) {
            mAdMobMadAdapter.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (mAdMobMadAdapter != null) {
            mAdMobMadAdapter.pause();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (mAdMobMadAdapter != null) {
            mAdMobMadAdapter.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdMobMadAdapter != null) {
            mAdMobMadAdapter.resume();
        }
    }
}
