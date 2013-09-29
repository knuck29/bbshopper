package com.knuck29.bbshopper.app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class BaseActionBarActivity extends Activity {

    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BBApplication) getApplication()).inject(this);

        mActionBar = getActionBar();

    }

}
