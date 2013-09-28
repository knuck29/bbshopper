package com.knuck29.bbshopper.modules;

/**
 * Created by knolker on 8/7/13.
 */
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface ForApplication {
}
