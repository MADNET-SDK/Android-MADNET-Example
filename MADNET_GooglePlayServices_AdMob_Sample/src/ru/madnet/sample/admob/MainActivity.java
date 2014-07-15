package ru.madnet.sample.admob;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

//Do not forget - you should add Google Play Services to use these classes!
import com.madnet.ads.mediation.admob.AdMobMadAdapter;
import ru.madnet.sample.admobsample.R;

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
        
        AdView admobview = new AdView(this);

        //MADNET will determine size of banner automatically - just use AdMob sizes
        admobview.setAdSize(AdSize.BANNER); 
        admobview.setAdUnitId(ADMOB_MEDIATION_ID); 
        mAdMobMadAdapter = new AdMobMadAdapter(admobview, ADMOB_CUSTOM_EVENT_LABEL, MADNET_SPACE_ID, true);        

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);        
        layout.addView(admobview);
        
        mAdMobMadAdapter.showBanners(new AdRequest.Builder()); 
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
