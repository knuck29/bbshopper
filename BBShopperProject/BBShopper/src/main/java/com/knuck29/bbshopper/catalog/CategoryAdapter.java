package com.knuck29.bbshopper.catalog;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.knuck29.bbshopper.R;

/**
 * Created by knolker on 9/29/13.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {
	private final int resourceId;
	private ArrayList<Category> mItems;

	public CategoryAdapter(Context context, int textViewResourceId, ArrayList<Category> items) {
		super(context, textViewResourceId, items);
		mItems = items;
		Collections.sort(mItems);
		resourceId = textViewResourceId;
	}

	public ArrayList<Category> getCategroies() {
		return mItems;
	}

	public void setCategroies(ArrayList<Category> categories) {
		mItems = categories;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Category category = mItems.get(position);
		if (category == null) {
			return null;
		}
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = convertView;
		if (view == null) {
			view = inflater.inflate(resourceId, parent, false);
		}
		else {
			view = convertView;
		}

		view.setTag(category);
		TextView terms = (TextView) view.findViewById(android.R.id.text1);
		if (terms != null) {
			terms.setText(String.format("%s", String.valueOf(category.getTitle())));
		}

		return view;
	}

}
