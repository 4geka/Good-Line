package com.example.ergo_proxy.goodlineapptest;

/**
 * Created by Ergo-Proxy on 14.05.2015.
 */
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ErgoProxy implements Comparable<ErgoProxy>, Parcelable {
    private  String mTitle;
    private  String mSmallDesc;
    private  String mImageUrl;
    private  String mArticleUrl;
    private  String mStringDate;
    public static DateFormat sJUD;

    public ErgoProxy(String title, String smallDesc, String imageUrl, String articleUrl, String stringDate) {
        this.mSmallDesc=smallDesc;
        this.mStringDate = stringDate;
        this.mTitle = title;
        this.mImageUrl = imageUrl;
        this.mArticleUrl=articleUrl;
        if(sJUD==null){
            Locale russian = new Locale("ru");
            String[] newMonths = {
                    "января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
            DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
            dfs.setMonths(newMonths);
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);
            SimpleDateFormat sdf = (SimpleDateFormat) df;
            sdf.setDateFormatSymbols(dfs);
            sJUD  =  new SimpleDateFormat("d MMMM yyyy, HH:mm", new Locale("ru"));
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        mArticleUrl = articleUrl;
    }

    public String getStringDate() {
        return mStringDate;
    }

    public void setStringDate(String stringDate) {
        mStringDate = stringDate;
    }

    @Override
    public int compareTo(ErgoProxy another) {

        try {
            Date currentDate =  sJUD.parse(mStringDate);
            Date anotherDate =  sJUD.parse(another.mStringDate);

            if(currentDate.before(anotherDate))
            {
                return -1;
            }
            else if(currentDate.after(anotherDate))
            {
                return 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mSmallDesc);
        dest.writeString(mImageUrl);
        dest.writeString(mArticleUrl);
        dest.writeString(mStringDate);
    }

    public static final Parcelable.Creator<ErgoProxy> CREATOR = new Parcelable.Creator<ErgoProxy>() {

        public ErgoProxy createFromParcel(Parcel in) {
            return new ErgoProxy(in.readString(), in.readString(), in.readString(),in.readString(),in.readString());
        }

        public ErgoProxy[] newArray(int size) {
            return new ErgoProxy[size];
        }
    };

    public String getSmallDesc() {
        return mSmallDesc;
    }

    public void setSmallDesc(String smallDesc) {
        mSmallDesc = smallDesc;
    }
}
