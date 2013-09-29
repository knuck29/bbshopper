package com.knuck29.bbshopper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.knuck29.bbshopper.app.BaseActionBarActivity;
import com.knuck29.bbshopper.catalog.Catalog;

import javax.inject.Inject;
import java.util.Observable;
import java.util.Observer;

public class CategoryActivity extends BaseActionBarActivity implements Observer{

    @Inject Catalog catalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catalog.addObserver(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }

    @Override
    public void update (Observable observable, Object data) {
        
    }
}
