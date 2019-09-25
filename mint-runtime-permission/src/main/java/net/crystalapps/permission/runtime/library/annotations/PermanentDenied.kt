package net.crystalapps.permission.runtime.library.annotations

import net.crystalapps.permission.runtime.library.callbacks.PermanentDeniedHandler
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass
import kotlin.annotation.Retention

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class PermanentDenied(val value: KClass<out PermanentDeniedHandler>)
