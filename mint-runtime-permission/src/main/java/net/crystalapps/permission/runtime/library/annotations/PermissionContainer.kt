package net.crystalapps.permission.runtime.library.annotations


/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class PermissionContainer(vararg val value: Permit)
