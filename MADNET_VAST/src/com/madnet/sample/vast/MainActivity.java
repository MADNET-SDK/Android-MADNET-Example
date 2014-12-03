package com.madnet.sample.vast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.madnet.ads.AdRequest;
import com.madnet.ads.AdResponseStatus;
import com.madnet.ads.AdVast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Deprecated
    /*
    FIXME - This is MADNET ad placement identifier. You should obtain your
    own at MADNET site and replace current value by your placementID.
    Annotation only to attract attention.
    */
    //private static final String MADNET_SPACE_ID = "YOUR_MADNET_ID - take it from madnet site!";
	private static final String MADNET_SPACE_ID = "152";
    
    private volatile boolean mClickable = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);
        ImageButton button = (ImageButton) findViewById(R.id.button_banner);
        button.setOnClickListener(this); 
    }

    public void onClick(View v) {
        showVastVideo();
    }

    private void showVastVideo() {
        // to avoid double-click launch, 
        if (!mClickable) {
            return;
        }
        mClickable = false;
        
        final AdVast vast = new AdVast(this, MADNET_SPACE_ID);
        vast.setTestmode(false);
        
        //listener is not necessary, but it helps to determine that banner is ready to be shown
        vast.setListener(new AdVast.AdVastListener() {

            public void onReady() {
                // If listener was set, banner WILL NOT be shown automatically, showBanner() must
                // be invoked manually, when banner is ready - or later, if needed
                vast.showBanner();
                mClickable = true;
            }

            public void onGetResponse(AdResponseStatus ars) {
                Toast.makeText(MainActivity.this, "Server response: " + ars, Toast.LENGTH_SHORT).show();
            }
        });         
        
        //There can be some targeting applied        
        AdRequest request = new AdRequest.Builder().getRequest();        
        
        //If listener was not set, loadBanner() will show VAST ad automatically, when loading will complete
        vast.loadBanner(request); 
    }
}
