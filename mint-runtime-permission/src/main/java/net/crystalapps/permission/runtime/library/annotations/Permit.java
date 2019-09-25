package net.crystalapps.permission.runtime.library.annotations;

import androidx.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permit {

    String value();
    @StringRes int whenDenied() default -1;
    @StringRes int whenPermanentDenied() default -1;
    boolean checkPermanentDenied() default true;
    boolean required() default true;
}
