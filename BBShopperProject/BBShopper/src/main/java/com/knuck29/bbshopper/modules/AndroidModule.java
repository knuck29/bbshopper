package com.knuck29.bbshopper.modules;

import javax.inject.Singleton;

import android.app.Application;
import android.content.Context;
import com.knuck29.bbshopper.app.BBApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by knolker on 8/7/13.
 */
@Module(library = true)
public class AndroidModule {
	private final Application mApplication;

	public AndroidModule(BBApplication application) {
        mApplication = application;
	}

	/**
	 * Allow the application context to be injected but require that it be
	 * annotated with {@link com.knuck29.bbshopper.modules.ForApplication @Annotation} to explicitly
	 * differentiate it from an activity context.
	 */
	@Provides @Singleton @ForApplication
	Context provideApplicationContext() {
		return mApplication.getApplicationContext();
	}

    @Provides
    @Singleton
    Context provideBaseContext() {
        return mApplication.getBaseContext();
    }
}
