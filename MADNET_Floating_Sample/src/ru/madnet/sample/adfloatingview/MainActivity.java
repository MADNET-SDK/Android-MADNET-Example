package ru.madnet.sample.adfloatingview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.madnet.ads.AdFloatingLayout;
import com.madnet.ads.AdRequest;

public class MainActivity extends Activity {

    //Fields to implement sample list with some content
    private static final int ROW_COUNT = 30;
    private static final String DATA_KEY = "Data_key";
    private String[] mFrom;
    private int[] mTo;
    private List<Map<String, String>> mDataList;
    private SimpleAdapter mAdapter;
    
    //Floating banner components - look into XML (activity_main) for details
    private AdFloatingLayout mAdFloatingView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mListView = (ListView) findViewById(android.R.id.list);
        mAdFloatingView = (AdFloatingLayout) findViewById(R.id.adFloatingLayout);

        //Banner will be attached to the "top" of the list
		mAdFloatingView.setupAd(mListView, AdFloatingLayout.IN_HEADER, true); 

        //Generating sample content of list view
        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);

        //Starting rotation
        mAdFloatingView.showBanners(new AdRequest.Builder().getRequest());
    }

    //Sample content generation for list
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
     You should notify floating view about lifecycle events to prevent
     memory leaks ant unnecessary requests to server.
     */  
    @Override
    protected void onDestroy() {
        mAdFloatingView.dismiss();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mAdFloatingView.pause();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mAdFloatingView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdFloatingView.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdFloatingView.resume();
    }
}
