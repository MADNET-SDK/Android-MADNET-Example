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

    //Эти поля использованы исключительно в целях примера - они отвечают за
    //содержимое демонстрируемого списка.
    private static final String DATA_KEY = "Data_key";
    private static final int ROW_COUNT = 30;
    private String[] mFrom;
    private int[] mTo;
    private List<Map<String, String>> mDataList;
    private SimpleAdapter mAdapter;
    
    //Компоненты, реализующие список и "плавающий" баннер (см. XML)
    private AdFloatingLayout mAdFloatingView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mListView = (ListView) findViewById(android.R.id.list);
        mAdFloatingView = (AdFloatingLayout) findViewById(R.id.adFloatingLayout);

        //В данном примере баннер будет закреплен в шапке списка
        mAdFloatingView.setAdViewPlace(AdFloatingLayout.IN_HEADER);

        //В данном примере баннер имеет кнопку закрытия.
        mAdFloatingView.useCustomClose(true);

        //Стандартная процедура привязки компонентов ListView и AdFloatingLayout
        mAdFloatingView.setListView(mListView);
        mListView.setOnScrollListener(mAdFloatingView);
        mAdFloatingView.attachAdViewToContainer();

        //Следующие 3 строчки - генерация строк для списка, сделано
        //исключительно для примера. Здесь должен быть ВАШ адаптер
        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);

        //Запускаем ротацию баннеров
        mAdFloatingView.showBanners(new AdRequest.Builder().getRequest());
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
