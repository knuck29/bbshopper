package com.knuck29.bbshopper.modules;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Created by knolker on 8/7/13.
 */
@Module(library = true)
public class AndroidModule {
	private final Context mContext;

	public AndroidModule(Context application) {
		mContext = application;
	}

	/**
	 * Allow the application context to be injected but require that it be
	 * annotated with {@link com.knuck29.bbshopper.modules.ForApplication @Annotation} to explicitly
	 * differentiate it from an activity context.
	 */
	@Provides @Singleton @ForApplication
	Context provideApplicationContext() {
		return mContext;
	}

}
