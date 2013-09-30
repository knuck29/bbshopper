package com.knuck29.bbshopper.catalog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.knuck29.bbshopper.R;
import com.squareup.picasso.Picasso;
import android.view.View.OnClickListener;

/**
 * Created by knolker on 9/29/13.
 */
public class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;
	private final int categoryResourceId;
    private final int productResourceId;
	private ArrayList<Product> mItems;
    private OnClickListener mImageClickListener;
    private final NumberFormat mCurrencyFormat;

	public ProductAdapter (Context context, int categoryResourceId, int productResourceId, ArrayList<Product> items, OnClickListener imageHandler) {
		super(context, categoryResourceId, items);
        mContext = context;
		mItems = items;
		Collections.sort(mItems);
        this.categoryResourceId = categoryResourceId;
        this.productResourceId = productResourceId;
        mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        mImageClickListener = imageHandler;
	}

	public ArrayList<Product> getCategories() {
		return mItems;
	}

	public void setCategories (ArrayList<Product> categories) {
		mItems = categories;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        Product product = mItems.get(position);
		if (product == null) {
			return null;
		}
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = convertView;
        if (product.getNodeType().equals(Product.NodeType.Product)){
            view = inflater.inflate(productResourceId, parent, false);
        }
        else {
            view = inflater.inflate(categoryResourceId, parent, false);
        }

		view.setTag(product);
		TextView terms = (TextView) view.findViewById(android.R.id.text1);
		if (terms != null) {
			terms.setText(String.format("%s", String.valueOf(product.getTitle())));
		}
        if (product.getNodeType().equals(Product.NodeType.Product)){
            ImageView imageFull = (ImageView)view.findViewById(R.id.imageFull);
            TextView priceValue = (TextView) view.findViewById(R.id.priceValue);
            TextView priceRetail = (TextView)view.findViewById(R.id.priceRetail);
            TextView priceSale = (TextView) view.findViewById(R.id.priceSale);
            if (product.getValuePrice() != 0){
                priceValue.setText(String.format("%s: %s %s", mContext.getString(R.string.price_value), mCurrencyFormat.format(product.getValuePrice()), product.getCurrency()));
                priceValue.setVisibility(View.VISIBLE);
            }
            if (product.getSalePrice() != 0){
                priceSale.setText(String.format("%s: %s %s", mContext.getString(R.string.price_sale),mCurrencyFormat.format(product.getSalePrice()), product.getCurrency()));
                priceSale.setVisibility(View.VISIBLE);
            }
            if (product.getRetailPrice() != 0){
                priceRetail.setText(String.format("%s: %s %s", mContext.getString(R.string.price_retail),mCurrencyFormat.format(product.getRetailPrice()), product.getCurrency()));
                priceRetail.setVisibility(View.VISIBLE);
            }
            if (product.getFullImage() != null){
                Picasso.with(mContext).load(product.getFullImage()).into(imageFull);
                imageFull.setTag(product.getFullImage());
                imageFull.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageFull.setOnClickListener(mImageClickListener);
            }
        }

		return view;
	}

}
