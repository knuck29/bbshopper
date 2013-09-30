package com.knuck29.bbshopper.app;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;

public class BaseActionBarListActivity extends ListActivity {

	protected ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		((BBApplication) getApplication()).inject(this);

		mActionBar = getActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

}
