package ru.madnet.sample.mopub;

import ru.madnet.sample.admobsample.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.mad.ad.AdMopubMadAdapter;
import com.mad.ad.Dimension;
import com.mopub.mobileads.MoPubView;
import java.util.Map;

public class MainActivity extends Activity {
   
    @Deprecated
    /*
    FIXME - This is MOPUB unit identifier. You should use here your own 
    MoPub ID, taken from MoPub site.
    Annotation only to attract attention.
    */
    private static final String MOPUB_UNIT_ID = "MOPUB_ID";
    
    @Deprecated
    /*
    FIXME - This is MADNET ad placement identifier. You should obtain your
    own at MADNET site and replace current value by your placementID.
    Annotation only to attract attention.
    */
    private static final String MADNET_SPACE_ID = "YOUR_MADNET_ID - take it from madnet site!";    
   
    private AdMopubMadAdapter mMopubAdapter;
    private MoPubView mMopubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Use here size, specified at MoPub site to this MoPud unit ID
        mMopubAdapter = new AdMopubMadAdapter(this, Dimension.MAD_SIZE_320x50, MADNET_SPACE_ID);
        
        mMopubView = new MoPubView(this);
        mMopubView.setAdUnitId(MOPUB_UNIT_ID); 
        
        /* Adapter provides prepared extra config - but you can add in this extra
        anything you need. */
        Map<String, Object> extras = mMopubAdapter.getLocalExtra();
        mMopubView.setLocalExtras(extras);
  
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);        
        layout.addView(mMopubView);
        
        mMopubView.loadAd();
    }
    
    /*
     You should notify adapter about lifecycle events to prevent
     memory leaks ant unnecessary requests to server.
     */       
    @Override
    protected void onDestroy() {
        if (mMopubAdapter != null) {
            mMopubAdapter.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (mMopubAdapter != null) {
            mMopubAdapter.pause();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (mMopubAdapter != null) {
            mMopubAdapter.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMopubAdapter != null) {
            mMopubAdapter.resume();
        }
    }
}
