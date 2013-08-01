package com.example.adviewinlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mad.ad.AdRequest;
import com.mad.ad.AdStaticView;
import com.mad.ad.Dimension;

public class MainActivity extends Activity {

    private static final int DATA_LENGTH = 20;
    private List<String> mRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRows = mock();
        ((ListView) findViewById(android.R.id.list)).setAdapter(new StandartBannerAdapter(this,
                android.R.layout.simple_list_item_1, mRows));
    }

    private List<String> mock() {
        List<String> mockedData = new ArrayList<String>();
        for (int i = 1; i <= DATA_LENGTH; i++) {
            mockedData.add(i + " row");
        }
        return mockedData;
    }

    static class StandartBannerAdapter extends ArrayAdapter<String> {

        private final int TYPE_CONTENT = 0;
        private final int TYPE_ADVIEW = 1;
        private List<String> mListOfRows;
        private LayoutInflater mLayoutInflater;

        public StandartBannerAdapter(Context context, int layoutResourseId, List<String> rows) {
            super(context, layoutResourseId, rows);
            this.mListOfRows = rows;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public String getItem(int position) {

            return mListOfRows.get(position);
        }

        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 4 == 2) {
                return TYPE_ADVIEW;
            }
            else {
                return TYPE_CONTENT;
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = createView(position, convertView);
                bindView(position, convertView);
            }
            else {
                bindView(position, convertView);
            }
            return convertView;
        }

        private void bindView(int position, View convertView) {
            if (convertView.getTag() instanceof AdViewHolder) {
                // do nothing, because of AdView
                return;
            }
            else {
                bindItemView(position, convertView);
            }
        }

        private void bindItemView(int position, View convertView) {
            String currentRowText = getItem(position);
            Object holder = convertView.getTag();
            if (convertView.getTag() instanceof ViewHolder) {
                ((ViewHolder) holder).textView.setText(currentRowText);
            }

        }

        private View createView(int position, View convertView) {
            int type = getItemViewType(position);
            switch (type) {
            case TYPE_CONTENT: {
                View v = createItemView();
                ViewHolder holder = new ViewHolder();
                v.setTag(holder);
                holder.textView = (TextView) v.findViewById(R.id.standart_banner_text);
                return v;
            }
            case TYPE_ADVIEW: {
                View v = createAdView();
                AdViewHolder holder = new AdViewHolder();
                v.setTag(holder);
                return v;
            }
            default: {
                View v = createItemView();
                ViewHolder holder = new ViewHolder();
                v.setTag(holder);
                holder.textView = (TextView) v.findViewById(R.id.standart_banner_text);
                return v;
            }
            }
        }

        private View createAdView() {
            return mLayoutInflater.inflate(R.layout.madnet_adview, null);
        }

        private View createItemView() {
            return mLayoutInflater.inflate(R.layout.standart_banner_item, null);
        }

    }

    private static class ViewHolder {
        TextView textView;
    }

    private static class AdViewHolder {

    }
}
