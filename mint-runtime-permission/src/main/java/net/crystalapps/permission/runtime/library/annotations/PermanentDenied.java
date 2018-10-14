package net.crystalapps.permission.runtime.library.annotations;

import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PermanentDenied {
    Class<? extends PermanentDeniedHandler> value();
}
