package ru.madnet.sample.admobfloating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.google.android.gms.ads.AdRequest;

//Do not forget - you should add Google Play Services to use these classes!
import com.madnet.ads.mediation.admob.AdMobFloatingLayout;
import com.madnet.ads.mediation.admob.AdMobMadAdapter;

public class MainActivity extends Activity {    
    
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
        
		/******** XML DESCRIPTION: *********/
		/*
		mad:admobExtraLabel = "some_fake_label"   // this should be REPLACED by your label's name
        mad:space_id="YOUR_MADENT_PLACEMENT_ID"   // REPLACE this value by your MADNET placement ID
        mad:testmode = "true"				      // don't forget to reset as FALSE in release of your App!
        mad:dimension="MAD_SIZE_320x50"           // use the same banner sizes of admob and MADNET
        ads:adSize="BANNER"                       // there, AdMob BANNER have the same size as MAD_SIZE_320x50
        ads:adUnitId="YOUR_ADMOB_MEDIATION_ID"    // REPLACE it by your AdMob Mediation ID
		*/
		
        // From XML
        mListView = (ListView) findViewById(android.R.id.list);
        mAdView = (AdMobFloatingLayout) findViewById(R.id.adLayout);
		
		mAdView.setupAd(mListView, AdMobFloatingLayout.IN_HEADER, true); 
       
        //Generating sample content of list view
        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);

        mAdView.showBanners(new AdRequest.Builder());
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
