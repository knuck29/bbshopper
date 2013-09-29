package com.knuck29.bbshopper.app;

import java.util.Arrays;
import java.util.List;

import android.app.Application;
import com.knuck29.bbshopper.modules.AndroidModule;
import com.knuck29.bbshopper.modules.BBCatalogModule;
import dagger.ObjectGraph;

/**
 * Created by knolker on 8/7/13.
 */
public class BBApplication extends Application {

	private ObjectGraph graph;

	@Override
	public void onCreate() {
		super.onCreate();

		graph = ObjectGraph.create(getModules().toArray());
	}

	protected List<Object> getModules() {
		return Arrays.asList(
                new AndroidModule(this.getApplicationContext()),
                new BBCatalogModule(this)
        );
	}

	public void inject(Object object) {
		graph.inject(object);
	}

}
