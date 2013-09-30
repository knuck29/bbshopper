package com.knuck29.bbshopper.modules;

import javax.inject.Singleton;

import android.content.Context;
import com.knuck29.bbshopper.app.BBApplication;
import com.knuck29.bbshopper.CategoryActivity;
import com.knuck29.bbshopper.catalog.Catalog;
import dagger.Module;
import dagger.Provides;

/**
 * Created by knolker on 8/7/13.
 */
@Module(injects = { CategoryActivity.class, Catalog.class }, complete = false, library = true)
public class BBCatalogModule {

	private final BBApplication mApplication;

	public BBCatalogModule (BBApplication app) {
		mApplication = app;
	}

	@Provides
	@Singleton
	Catalog provideCatalog(Context context) {
		return new Catalog(context);
	}

}
