package com.example.ergo_proxy.goodlineapptest;

/**
 * Created by Ergo-Proxy on 14.05.2015.
 */
import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import volley.VolleyApplication;


public class GoodlineFragment extends Fragment {

    private static final String TAG= "GoodlineFragment";
    private ImageView mTitleImageView;
    private TextView mTitleView;
    private TextView mArticleView;
    private ErgoProxy mErgoNews;

    public GoodlineFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mErgoNews      = (ErgoProxy) getActivity().getIntent().getParcelableExtra(GoodlineTwoFragment.SELECTED_NEWS);
        mTitleImageView = (ImageView)getView().findViewById(R.id.title_image);
        mTitleView      = (TextView) getView().findViewById(R.id.titleView);
        mArticleView      = (TextView) getView().findViewById(R.id.article_content);
        mTitleView.setText(mErgoNews.getTitle());

        Picasso.with(getActivity())
                .load(mErgoNews.getImageUrl())
                .fit()
                .centerCrop()
                .into(mTitleImageView);

        getActivity().setTitle(mErgoNews.getTitle());
        fetchArticle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_fragment, container, false);
    }
    private void fetchArticle(){

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, mErgoNews.getArticleUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String articleHTML = parseArticle(response);
                        mArticleView.setText(Html.fromHtml(articleHTML,
                                new PicassoImageGetter(
                                        mArticleView,
                                        Resources.getSystem(),
                                        Picasso.with(getActivity())
                                ), null));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.getMessage());
            }
        });
        VolleyApplication.getInstance().getRequestQueue().add(stringRequest);
    }
    private String parseArticle(String HTML){
        Document doc =null;
        try {
            doc = Jsoup.parse(HTML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(null,e.getMessage(),e);
        }
        Elements article = doc.select(".topic-content.text");
        return article.html();
    }

    public class PicassoImageGetter implements Html.ImageGetter {

        final Resources resources;

        final Picasso pablo;

        final TextView textView;

        public PicassoImageGetter(final TextView textView, final Resources resources, final Picasso pablo) {
            this.textView = textView;
            this.resources = resources;
            this.pablo = pablo;
        }

        @Override
        public Drawable getDrawable(final String source) {
            final BitmapDrawablePlaceHolder result = new BitmapDrawablePlaceHolder();

            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(final Void... meh) {
                    try {
                        return pablo.load(source).get();
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(final Bitmap bitmap) {
                    try {
                        final BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);

                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                        result.setDrawable(drawable);
                        result.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                        textView.setText(textView.getText());
                    } catch (Exception e) {

                    }
                }

            }
                    .execute((Void) null);

            return result;
        }

        class BitmapDrawablePlaceHolder extends BitmapDrawable {

            protected Drawable drawable;

            @Override
            public void draw(final Canvas canvas) {
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }

            public void setDrawable(Drawable drawable) {
                this.drawable = drawable;
            }

        }
    }
}