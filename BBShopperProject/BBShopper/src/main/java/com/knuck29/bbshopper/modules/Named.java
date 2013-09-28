package com.knuck29.bbshopper.modules;

/**
 * Created by knolker on 8/7/13.
 */

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface Named {
    String value() default "";
}
