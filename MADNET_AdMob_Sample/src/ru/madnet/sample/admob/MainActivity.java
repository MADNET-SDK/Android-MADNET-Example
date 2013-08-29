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

    //Это название частного события в медиации - его вы должны были указать
    //на сайте AdMob в связке с названием класса-медиатора MADNET SDK
    //ЗДЕСЬ ДОЛЖНО БЫТЬ НАЗВАНИЕ ВАШЕГО LABEL'А
    private static final String ADMOB_CUSTOM_EVENT_LABEL = "mAdtestevent";
    
    //ID медиации, выдается сайтом AdMob
    //ЗДЕСЬ ДОЛЖЕН БЫТЬ ВАШ MEDIATION ID
    private static final String ADMOB_MEDIATION_ID = "e1a33043e60c4126";
    
    //ID для MADNET SDK, выдается на сайте MADNET
    //ЗДЕСЬ ДОЛЖЕН БЫТЬ ВАШ MADNET ID
    private static final String MADNET_SPACE_ID = "2";
    
    //На данный момент это поле не используется MADNET SDK, но оно будет 
    //задействовано в будущем для возможности выбора предпостительного 
    //поставщика баннеров
    private static final String MADNET_PARTNER_ID = null;
    
    private AdMobMadAdapter mAdMobMadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Обратите внимание, Dimension.MAD_SIZE_320x50 совпадает с габаритами 
        //баннера AdMob AdSize.BANNER. Для использования других размеров вам
        //будет необходимо подобрать совпадающие размеры сетей AdMob и MADNET
        AdView admobview = new AdView(this, AdSize.BANNER, ADMOB_MEDIATION_ID);
        mAdMobMadAdapter = new AdMobMadAdapter(this, Dimension.MAD_SIZE_320x50, MADNET_PARTNER_ID, MADNET_SPACE_ID);
        
        CustomEventExtras extra = new CustomEventExtras().addExtra(ADMOB_CUSTOM_EVENT_LABEL, mAdMobMadAdapter);        

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);        
        layout.addView(admobview);
        
        //Не забудьте в запрос AdMob добавить дополнительную информацию
        //об адаптере MADNET для AdMob - ".setNetworkExtras(extra)"
        admobview.loadAd(new AdRequest().setNetworkExtras(extra));

    }
    
    
    @Override
    protected void onDestroy(){    
        mAdMobMadAdapter.dismiss();
        super.onDestroy();
    }
    
        @Override
    protected void onStop() {
        mAdMobMadAdapter.pause();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mAdMobMadAdapter.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdMobMadAdapter.resume();
    }
}
