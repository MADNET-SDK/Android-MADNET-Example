package ru.madnet.sample.adfloatingview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mad.ad.AdFloatingLayout;
import com.mad.ad.AdRequest;

public class MainActivity extends Activity {

    private static final String DATA_KEY = "Data_key";
    private static final int ROWS_NUMBER = 30;
    private AdFloatingLayout mAdFloatingView;
    private ListView mListView;
    private String[] mFrom;
    private int[] mTo;
    private List<Map<String, String>> mDataList;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(android.R.id.list);
        mAdFloatingView = (AdFloatingLayout) findViewById(R.id.adFloatingLayout);

        // Если вы хотите расположить баннер по верхней границе, используйте
        // AdFloatingView.IN_HEADER, если по верхней - AdFloatingView.IN_FOOTER.
        mAdFloatingView.setAdViewPlace(AdFloatingLayout.IN_HEADER);

        // Используйте этот метод, чтобы показать/спрятать кнопку закрытия
        // баннера
        mAdFloatingView.useCustomClose(true);

        // Установите для AdFloatingView ваш ListView
        mAdFloatingView.setListView(mListView);
        mListView.setOnScrollListener(mAdFloatingView);

        // Зафиксируйте компоненты
        mAdFloatingView.attachAdViewToContainer();

        // Только после этих действий можно устанавливать адаптеры для ListView

        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);
        // Запустите ротацию баннеров. Это можно сделать и позже.
        mAdFloatingView.showBanners(new AdRequest.Builder().getRequest());
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
