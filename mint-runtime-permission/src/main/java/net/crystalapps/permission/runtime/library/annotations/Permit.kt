package net.crystalapps.permission.runtime.library.annotations

import androidx.annotation.StringRes
import java.lang.annotation.RetentionPolicy
import kotlin.annotation.Retention

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Permit(
        val value: String,
        @StringRes val whenDenied: Int = -1,
        @StringRes val whenPermanentDenied: Int = -1,
        val checkPermanentDenied: Boolean = true,
        val required: Boolean = true
)
