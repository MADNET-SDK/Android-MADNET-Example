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
import com.mad.ad.AdStaticView;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {

    //Число строк в списке этого примера
    private static final int ROW_COUNT = 20;
    //Содержимое строк списка
    private List<String> mRows;
    
    private StandartBannerAdapter mListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRows = generateSampleRows();
        ListView listView = ((ListView) findViewById(android.R.id.list));
        mListAdapter = new StandartBannerAdapter(this, android.R.layout.simple_list_item_1, mRows);
        listView.setAdapter(mListAdapter);
    }

    private List<String> generateSampleRows() {
        List<String> rows = new ArrayList<String>();
        for (int i = 1; i <= ROW_COUNT; i++) {
            rows.add(i + " row");
        }
        return rows;
    }

    //Аналогично обычному контейнеру, определяем поведение списка при изменении
    //состояния ЖЦ активити - только в этом случае управление отдается адаптеру,
    //чтобы он сам оповестил все используемые контейнеры
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

    static class StandartBannerAdapter extends ArrayAdapter<String> {

        //Простое разделение по типам ячеек - либо стандартная, либо рекламная
        private final int TYPE_CONTENT = 0;
        private final int TYPE_ADVIEW = 1;
        
        private List<String> mRows;
        private LayoutInflater mLayoutInflater;
        
        //Адаптер будет хранить ссылки на все используемые рекламные контейнеры
        //чтобы можно было их все разом поставить на паузу или уничтожить
        private Set<AdStaticView> mAdViews = new HashSet<AdStaticView>();

        public StandartBannerAdapter(Context context, int layoutResourseId, List<String> rows) {
            super(context, layoutResourseId, rows);
            this.mRows = rows;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        //Здесь своеобразный "фасад" для управления ЖЦ баннеров - 
        //только действия применяются ко всем рекламным контейнерам списка
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
            //Число разновидностей ячеек
            //в данном примере их 2: обычные и рекламные
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            //Один из способов реализации периодичности рекламы в списке
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
            //Выполнянем связывание только для обычных ячеек
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
                    //Ветка в данном примере идентична ветке default,
                    //оставлено для целостности примера
                    return createItemView();
                }
                case TYPE_ADVIEW: {
                    return createAdView();
                }
                default: {
                    return createItemView();
                }
            }
        }        

        private View createAdView() {
            View adView = mLayoutInflater.inflate(R.layout.madnet_adview, null);
            AdViewHolder holder = new AdViewHolder();
            adView.setTag(holder);     
            
            //См. XML
            //Регистрируем созданный рекламный контейнер в наборе нашего списка
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

    //Класс-оболочка для обычной ячейки. В Вашем приложении он,
    //вероятно, будет более разнообразен
    private static class ViewHolder {

        TextView textView;
    }

    //Класс-оболочка для рекламной ячейки. От него ничего не требуется, кроме
    //предоставления возможности идентификации - в нашем примере это происходит
    //путём проверки принадлжености ячейки к этому классу
    private static class AdViewHolder {
        
    }
}
