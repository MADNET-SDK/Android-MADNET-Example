package ru.madnet.sample.admobfloating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.ads.mediation.customevent.CustomEventExtras;
import com.mad.ad.AdFloatingLayout;
import com.mad.ad.AdMobFloatingLayout;
import com.mad.ad.AdMobMadAdapter;

import com.mad.ad.Dimension;

public class MainActivity extends Activity {
    
    /*
    This is label of AdMob custom event. You should specify it as a
    labled's name at AdMob site.
    
    FIXME - do not forget to change ads:adUnitId in XML!
    */
    private static final String ADMOB_CUSTOM_EVENT_LABEL = "MADNET_CUSTOM_EVENT_LABEL";
    
    @Deprecated
    /*
    FIXME - This is MADNET ad placement identifier. You should obtain your
    own at MADNET site and replace current value by your placementID.
    Annotation only to attract attention.
    */
    private static final String MADNET_SPACE_ID = "YOUR_MADNET_ID - take it from madnet site!";
    
    //List and floating banner components (look in XML)
    private ListView mListView;
    private AdMobFloatingLayout mAdView;
    private AdMobMadAdapter admobAdapter;

    /*
    Some field, responsible to generate sample list content.
    */
    private static final String DATA_KEY = "Data_key";
    private static final int ROW_COUNT = 30;
    private String[] mFrom;
    private int[] mTo;
    private List<Map<String, String>> mDataList;
    private SimpleAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // From XML
        mListView = (ListView) findViewById(android.R.id.list);
        mAdView = (AdMobFloatingLayout) findViewById(R.id.adLayout);
        
        //Fits banner to footer of list view
        mAdView.setAdViewPlace(AdFloatingLayout.IN_FOOTER);
        
        //Makes "close button" enabled
        mAdView.useCustomClose(true);
        
        //Linking ListView and AdMobFloatingLayout...
        mAdView.setListView(mListView);
        mListView.setOnScrollListener(mAdView);
        mAdView.attachAdViewToContainer();

        CustomEventExtras extra = new CustomEventExtras();
        admobAdapter = new AdMobMadAdapter(this, new Dimension(320, 50), MADNET_SPACE_ID); 
        
        //Generating smaple content of list view
        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);

        //Starting rotation
        extra.addExtra(ADMOB_CUSTOM_EVENT_LABEL, admobAdapter);
        mAdView.loadAd(new com.google.ads.AdRequest().setNetworkExtras(extra));
    }

    private void initAdaptersData() {

        mFrom = new String[] { DATA_KEY };
        mTo = new int[] { android.R.id.text1 };

        mDataList = new ArrayList<Map<String, String>>();

        for (int i = 1; i < ROW_COUNT; i++) {
            Map<String, String> dataRow = new HashMap<String, String>();
            dataRow.put(DATA_KEY, i + " row");
            mDataList.add(dataRow);
        }
    }
    
    /*
     You should notify adapter about lifecycle events to prevent
     memory leaks ant unnecessary requests to server.
     */   
    @Override
    protected void onDestroy(){    
        if (admobAdapter != null){
            admobAdapter.dismiss();
        }
        super.onDestroy();
    }
    
    @Override
    protected void onStop() {
        if (admobAdapter != null) {
            admobAdapter.pause();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (admobAdapter != null) {
            admobAdapter.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (admobAdapter != null) {
            admobAdapter.resume();
        }
    }
}
