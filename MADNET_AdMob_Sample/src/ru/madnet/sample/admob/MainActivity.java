package ru.madnet.sample.admob;


import ru.madnet.sample.admobsample.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.mediation.customevent.CustomEventExtras;
import com.mad.ad.AdMobMadAdapter;
import com.mad.ad.Dimension;

public class MainActivity extends Activity {

    private AdMobMadAdapter mAdMobMadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdMobMadAdapter = new AdMobMadAdapter(this, Dimension.MAD_SIZE_320x50, null, "2");
        CustomEventExtras extra = new CustomEventExtras();
        // mAdtestevent - название(Label) customEvent, указанное на сайте AdMob
        extra.addExtra("mAdtestevent", mAdMobMadAdapter);
        AdView admobview = new AdView(this, AdSize.BANNER, "e1a33043e60c4126");

        // ... Размещение admobview
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);        
        layout.addView(admobview);
        
        // Загрузка баннеров
        admobview.loadAd(new AdRequest().setNetworkExtras(extra));

    }
    @Override
    protected void onDestroy(){    
        mAdMobMadAdapter.dismiss();
        super.onDestroy();
    }
}
