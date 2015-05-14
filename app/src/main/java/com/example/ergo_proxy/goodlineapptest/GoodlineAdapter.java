package com.example.ergo_proxy.goodlineapptest;

/**
 * Created by Ergo-Proxy on 14.05.2015.
 */
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class GoodlineAdapter extends ArrayAdapter<ErgoProxy> {

    private ArrayList<ErgoProxy> mNewslist;
    private final Context mContext;
    public GoodlineAdapter(Context context) {
        super(context, R.layout.main_items);
        mNewslist=new ArrayList<>();
        mContext=context;
    }

    public void addNewslist(ArrayList<ErgoProxy> parsedNewsList) {
        mNewslist.addAll(parsedNewsList);
        notifyDataSetChanged();
    }
    public List<ErgoProxy> getNewsList() {
        return mNewslist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_items, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.news_image);
        TextView titleView = (TextView) convertView.findViewById(R.id.news_title);
        TextView dateView = (TextView) convertView.findViewById(R.id.news_date);

        ErgoProxy newsItem = mNewslist.get(position);
        Picasso.with(getContext())
                .load(newsItem.getImageUrl())
                .into(imageView);
        titleView.setText(newsItem.getTitle());
        dateView.setText(newsItem.getStringDate());

        if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            TextView descView = (TextView) convertView.findViewById(R.id.news_desc);
            descView.setText(newsItem.getSmallDesc());

        }

        return convertView;
    }
    @Override
    public int getCount() {
        return mNewslist.size();
    }

    @Override
    public ErgoProxy getItem(int position) {
        return mNewslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void prependNewsList(ArrayList<ErgoProxy> newsList) {
        for(ErgoProxy ergoProxy: newsList){
            mNewslist.add(0,ergoProxy);
        }
        notifyDataSetChanged();
    }
}