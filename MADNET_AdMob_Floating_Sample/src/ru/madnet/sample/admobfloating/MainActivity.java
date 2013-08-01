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

    private ListView mListView;
    private AdMobFloatingLayout mAdView;
    private AdMobMadAdapter admobAdapter;

    private static final String DATA_KEY = "Data_key";
    private static final int ROWS_NUMBER = 30;

    private String[] mFrom;
    private int[] mTo;
    private List<Map<String, String>> mDataList;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(android.R.id.list);
        mAdView = (AdMobFloatingLayout) findViewById(R.id.adLayout);
        mAdView.setAdViewPlace(AdFloatingLayout.IN_FOOTER);
        mAdView.useCustomClose(true);
        mAdView.setListView(mListView);
        mListView.setOnScrollListener(mAdView);
        mAdView.attachAdViewToContainer();

        CustomEventExtras extra = new CustomEventExtras();
        admobAdapter = new AdMobMadAdapter(this, new Dimension(320, 50), "2", null);

        extra.addExtra("mAdtestevent", admobAdapter);

        mAdView.loadAd(new com.google.ads.AdRequest().setNetworkExtras(extra));

        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);

    }

    private void initAdaptersData() {

        mFrom = new String[] { DATA_KEY };
        mTo = new int[] { android.R.id.text1 };

        mDataList = new ArrayList<Map<String, String>>();

        for (int i = 1; i < ROWS_NUMBER; i++) {
            Map<String, String> dataRow = new HashMap<String, String>();
            dataRow.put(DATA_KEY, i + " row");
            mDataList.add(dataRow);
        }
    }
}
