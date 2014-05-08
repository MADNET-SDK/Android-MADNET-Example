package com.example.adviewinlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.madnet.ads.AdStaticView;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {

    //Row count of list - only as sample
    private static final int ROW_COUNT = 20;
    //Content of rows
    private List<String> mRows;
    
    private StandartBannerAdapter mListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Lets generate some sample content!
        mRows = generateSampleRows();
        mListAdapter = new StandartBannerAdapter(this, android.R.layout.simple_list_item_1, mRows);
        
        ListView listView = ((ListView) findViewById(android.R.id.list));
        listView.setAdapter(mListAdapter);
    }

    private List<String> generateSampleRows() {
        List<String> rows = new ArrayList<String>();
        for (int i = 1; i <= ROW_COUNT; i++) {
            rows.add(i + " row");
        }
        return rows;
    }

    /*
     You should notify adapter about lifecycle events to prevent
     memory leaks ant unnecessary requests to server.
     */ 
    @Override
    protected void onDestroy() {
        mListAdapter.dismiss();
        super.onDestroy();        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mListAdapter.resume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mListAdapter.pause();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        mListAdapter.pause();
    }

    /**
     * Class, that implements Array Adapter fuctionality with 
     * injections of several advertisement rows.
     */
    static class StandartBannerAdapter extends ArrayAdapter<String> {

        //Spliting our cells into two types: usual and advertisement
        private final int TYPE_CONTENT = 0;
        private final int TYPE_ADVIEW = 1;
        
        private final List<String> mRows;
        private final LayoutInflater mLayoutInflater;
        
        /*
        We should keep links to all advertisement containers - to let them
        know, when they should be paused or destroyed
        */
        private final Set<AdStaticView> mAdViews = new HashSet<AdStaticView>();

        public StandartBannerAdapter(Context context, int layoutResourseId, List<String> rows) {
            super(context, layoutResourseId, rows);
            this.mRows = rows;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        /*
        There adapter delegates lifecycle event to ALL advertisement 
        containers - no one should ignore this event.
        */
        public void pause(){
            for (AdStaticView adView: mAdViews){
                adView.pause();
            }
        }
        
        public void resume(){
            for (AdStaticView adView : mAdViews) {         
                adView.resume();
            }
        }
        
        public void dismiss(){
            for (AdStaticView adView : mAdViews) {
                adView.dismiss();
            }
        }

        @Override
        public String getItem(int position) {
            return mRows.get(position);
        }

        @Override
        public int getViewTypeCount() {
            //count of row types - we have two (usual and advertisement)
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            //determines periodicy of advertisements in list - each fourth
            //row, starting at index=2
            if (position % 4 == 2) {
                return TYPE_ADVIEW;
            } else {
                return TYPE_CONTENT;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = createView(position);   
            }
            Object holder = convertView.getTag();
            
            //For usual cells
            if (!(holder instanceof AdViewHolder)) {
                String currentRowText = getItem(position);
                ((ViewHolder) holder).textView.setText(currentRowText);
            }
            return convertView;
        }

        private View createView(int position) {
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_CONTENT: {
                    return createItemView();
                }
                case TYPE_ADVIEW: {
                    return createAdView();
                }
                default: {
                    //the same as TYPE_CONTENT - just a code-style "case"
                    return createItemView();
                }
            }
        }        

        private View createAdView() {
            View adView = mLayoutInflater.inflate(R.layout.madnet_adview, null);
            AdViewHolder holder = new AdViewHolder();
            adView.setTag(holder);     
            
            //There we registering advertisement container in our "link keeper"
            mAdViews.add((AdStaticView) ((RelativeLayout) adView).findViewById(R.id.adView));  
            return adView;            
        }

        private View createItemView() {
            View itemView = mLayoutInflater.inflate(R.layout.standart_banner_item, null);
            ViewHolder holder = new ViewHolder();
            itemView.setTag(holder);
            holder.textView = (TextView) itemView.findViewById(R.id.standart_banner_text);
            return itemView;
        }
    }

    /*
    Simple holder for usual row - in Your app it can consist of much more
    elements, not only the TextView
    */
    private static class ViewHolder {

        TextView textView;
    }

    /*
    Wrapper for Ad-row element. Just to split classes.
    */
    private static class AdViewHolder {
        
    }
}
