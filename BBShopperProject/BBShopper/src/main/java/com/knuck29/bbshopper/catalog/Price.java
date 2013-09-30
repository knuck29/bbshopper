package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/29/13.
 */
public class Price {

    double mValue;
    double mSale;
    double mRetail;
    String mCurrency;

    public Price (double value, String currency){
        mValue = value;
        mCurrency = currency;
    }

    public double getValue () {
        return mValue;
    }

    public void setValue (double value) {
        this.mValue = value;
    }

    public double getSale () {
        return mSale;
    }

    public void setSale (double sale) {
        this.mSale = sale;
    }

    public double getRetail () {
        return mRetail;
    }

    public void setRetail (double retail) {
        this.mRetail = retail;
    }

    public String getCurrency () {
        return mCurrency;
    }

    public void setCurrency (String currency) {
        this.mCurrency = currency;
    }
}
