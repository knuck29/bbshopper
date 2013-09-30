package com.knuck29.bbshopper;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.knuck29.bbshopper.app.BaseActionBarListActivity;
import com.knuck29.bbshopper.catalog.Catalog;
import com.knuck29.bbshopper.catalog.Category;
import com.knuck29.bbshopper.catalog.CategoryAdapter;

public class CategoryActivity extends BaseActionBarListActivity implements Observer, AdapterView.OnItemClickListener {

	// private static String BASE_URL = "https://emsapi.bbhosted.com";

	@Inject Catalog catalog;
	CategoryAdapter categoryAdapter = null;
	String mParentNodeUrl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		catalog.addObserver(this);
		catalog.loadCatalog(getString(R.string.base_url));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			catalog.navigateBreadcrumbBack();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void update(Observable observable, Object data) {
		if (categoryAdapter == null) {
			categoryAdapter = new CategoryAdapter(CategoryActivity.this, android.R.layout.simple_list_item_1,
					catalog.getCategories());
			getListView().setAdapter(categoryAdapter);
			getListView().setOnItemClickListener(this);
		}
		else {
			categoryAdapter.setCategroies(catalog.getCategories());
			categoryAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if ((view != null) && (view.getTag() != null) && view.getTag().getClass().equals(Category.class)) {
			Category category = (Category) view.getTag();
			if (category.getHref() != null) {
				catalog.loadCatalog(String.format("%s%s", getString(R.string.base_url), category.getHref()));
			}
		}
	}
}
