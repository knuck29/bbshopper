package com.knuck29.bbshopper.modules;

import javax.inject.Singleton;

import com.knuck29.bbshopper.catalog.Catalog;
import dagger.Module;
import dagger.Provides;

/**
 * Created by knolker on 8/7/13.
 */
@Module(injects = { Catalog.class }, complete = false)
public class BBCatalogModule {

	private final com.knuck29.bbshopper.BBApplication mApplication;

	public BBCatalogModule (com.knuck29.bbshopper.BBApplication app) {
		mApplication = app;
	}

	@Provides
	@Singleton
	Catalog provideCatalog() {
		return new Catalog();
	}

//	@Provides
//	@Singleton
//	DataManager provideDataManager() {
//		DataManager dataManager = new DataManager();
//		mApplication.inject(dataManager);
//		return dataManager;
//		// return new DataManager(mApplication.getApplicationContext());
//	}

}
