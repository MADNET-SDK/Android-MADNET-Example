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
    
    //Это название частного события в медиации - его вы должны были указать
    //на сайте AdMob в связке с названием класса-медиатора MADNET SDK
    //ЗДЕСЬ ДОЛЖНО БЫТЬ НАЗВАНИЕ ВАШЕГО LABEL'А
    private static final String ADMOB_CUSTOM_EVENT_LABEL = "mAdtestevent";
    
    //ID для MADNET SDK, выдается на сайте MADNET
    //ЗДЕСЬ ДОЛЖЕН БЫТЬ ВАШ MADNET ID
    private static final String MADNET_SPACE_ID = "2";
    
    //На данный момент это поле не используется MADNET SDK, но оно будет 
    //задействовано в будущем для возможности выбора предпостительного 
    //поставщика баннеров
    private static final String MADNET_PARTNER_ID = null;

    //Компоненты, реализующие список и "плавающий" баннер (см. XML)
    private ListView mListView;
    private AdMobFloatingLayout mAdView;
    private AdMobMadAdapter admobAdapter;

    //Эти поля использованы исключительно в целях примера - они отвечают за
    //содержимое демонстрируемого списка.
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
        
        // См. XML
        mListView = (ListView) findViewById(android.R.id.list);
        mAdView = (AdMobFloatingLayout) findViewById(R.id.adLayout);
        
        //В данном примере баннер будет закреплен в нижней части списка
        mAdView.setAdViewPlace(AdFloatingLayout.IN_FOOTER);
        
        //В данном примере баннер имеет кнопку закрытия.
        mAdView.useCustomClose(true);
        
        //Стандартная процедура привязки компонентов ListView и AdMobFloatingLayout
        mAdView.setListView(mListView);
        mListView.setOnScrollListener(mAdView);
        mAdView.attachAdViewToContainer();

        CustomEventExtras extra = new CustomEventExtras();
        admobAdapter = new AdMobMadAdapter(this, new Dimension(320, 50), MADNET_PARTNER_ID, MADNET_SPACE_ID); 
        
        //Следующие 3 строчки - генерация строк для списка, сделано
        //исключительно для примера. Здесь должен быть ВАШ адаптер
        initAdaptersData();
        mAdapter = new SimpleAdapter(this, mDataList, android.R.layout.simple_list_item_1, mFrom, mTo);
        mListView.setAdapter(mAdapter);

        //Запускаем ротацию баннеров
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
}
