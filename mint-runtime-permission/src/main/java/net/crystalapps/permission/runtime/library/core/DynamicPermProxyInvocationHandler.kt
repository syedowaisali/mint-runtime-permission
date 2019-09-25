package net.crystalapps.permission.runtime.library.core

import androidx.fragment.app.FragmentActivity
import net.crystalapps.permission.runtime.library.annotations.PermanentDenied
import net.crystalapps.permission.runtime.library.annotations.PermissionContainer
import net.crystalapps.permission.runtime.library.annotations.PermissionFailure
import net.crystalapps.permission.runtime.library.annotations.Permit
import net.crystalapps.permission.runtime.library.callbacks.*
import net.crystalapps.permission.runtime.library.config.RuntimePermissionConfig
import net.crystalapps.permission.runtime.library.models.Perm
import net.crystalapps.permission.runtime.library.utils.ObjectUtil
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*

/**
 * Created by Syed Owais Ali on 10/13/2018.
 */

class DynamicPermProxyInvocationHandler<T : FragmentActivity, S>(private val act: T, private val service: Class<S>) : InvocationHandler {

    override fun invoke(o: Any, method: Method, objects: Array<Any>?): Any? {

        if (method.returnType.name != "void") {
            throw IllegalStateException("Return type of " + method.name + " method should be void")
        }

        if (objects == null) {
            throw IllegalStateException(Request::class.java.name + " parameter not found in " + method.name + " method")
        }

        if (objects.size != 1) {
            throw IllegalStateException(method.name + " method should be contain only 1 parameter")
        }

        if (objects[0] !is Function<*>) {
            throw IllegalArgumentException("Invalid parameter found in " + method.name + " method, param type should be " + Request::class.java.name)
        }

        if (!method.isAnnotationPresent(Permit::class.java) && !method.isAnnotationPresent(PermissionContainer::class.java)) {
            throw IllegalStateException("Add at least one Permit or PermissionContainer annotation in " + method.name + " method")
        }

        val failedPermissionsList = ArrayList<Permit>()
        val serviceMethod = ServiceMethod(
                normalize(method),
                failedPermissionsList,
                objects[0] as () -> Unit,
                getPermanentDeniedHandler(method, service),
                getPermissionFailureHandler(method, service),
                method
        )

        process(serviceMethod)

        return null
    }

    private fun normalize(method: Method): Queue<Permit> {
        val queue = LinkedList<Permit>()

        if (method.isAnnotationPresent(Permit::class.java)) {
            queue.add(getAnnotation(method, Permit::class.java))
        }

        if (method.isAnnotationPresent(PermissionContainer::class.java)) {
            val container = getAnnotation(method, PermissionContainer::class.java)
            queue.addAll(Arrays.asList(*container.value))
        }

        return queue
    }

    private fun <A> getAnnotation(method: Method, clazz: Class<A>): A {
        var annotation: A? = null
        for (anno in method.declaredAnnotations) {
            anno as java.lang.annotation.Annotation
            if (anno.annotationType() == clazz) {
                annotation = anno as A
            }
        }

        return annotation!!
    }

    private fun getPermanentDeniedHandler(method: Method, service: Class<S>): PermanentDeniedHandler {
        var clazz: Class<out PermanentDeniedHandler>? = null

        if (method.isAnnotationPresent(PermanentDenied::class.java)) {
            clazz = method.getAnnotation(PermanentDenied::class.java).value.java
        } else if (service.isAnnotationPresent(PermanentDenied::class.java)) {
            clazz = service.getAnnotation(PermanentDenied::class.java)?.value?.java
        }

        return if (clazz != null) ObjectUtil.requireNonNull(initO(clazz))!! else RuntimePermissionConfig.instance.getPermanentDeniedHandler()
    }

    private fun getPermissionFailureHandler(method: Method, service: Class<S>): PermissionFailureHandler? {
        var clazz: Class<out PermissionFailureHandler>? = null

        if (method.isAnnotationPresent(PermissionFailure::class.java)) {
            clazz = method.getAnnotation(PermissionFailure::class.java).value.java
        } else if (service.isAnnotationPresent(PermissionFailure::class.java)) {
            clazz = service.getAnnotation(PermissionFailure::class.java)?.value?.java
        }

        return clazz?.let { initO(it) }
                ?: RuntimePermissionConfig.instance.getPermissionFailureHandler()
    }

    private fun <O> initO(clazz: Class<O>): O? {
        try {
            val constructor = clazz.getDeclaredConstructor()
            constructor.isAccessible = true
            return constructor.newInstance() as O
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    private fun process(serviceMethod: ServiceMethod) {
        val permit = serviceMethod.permitQueue.poll()

        if (permit != null) {

            RuntimePermission.requestPermission(act, permit.value, object : PermissionCallback<FragmentActivity> {
                override fun onGranted(perm: Perm<FragmentActivity>) {
                    process(serviceMethod)
                }

                override fun onDenied(perm: Perm<FragmentActivity>) {
                    if (permit.required) {
                        serviceMethod.addToFailureList(permit)
                    }
                    process(serviceMethod)
                }

                override fun onPermanentDenied(perm: Perm<FragmentActivity>) {
                    if (permit.required) {
                        serviceMethod.addToFailureList(permit)
                    }

                    if (permit.checkPermanentDenied) {
                        serviceMethod
                                .permanentDeniedHandler
                                .onHandle(permit, perm.caller, object : SettingOpener<FragmentActivity> {
                                    override fun open(caller: FragmentActivity, permission: String) {
                                        RuntimePermission.openAppSettingIntent(perm.caller, perm.type, object : PermissionSettingCallback<FragmentActivity> {
                                            override fun onGranted(perm: Perm<FragmentActivity>) {
                                                if (permit.required) {
                                                    serviceMethod.removeToFailureList(permit)
                                                }
                                                process(serviceMethod)
                                            }

                                            override fun onDenied(perm: Perm<FragmentActivity>) {
                                                process(serviceMethod)
                                            }
                                        })
                                    }

                                    override fun doNothing(caller: FragmentActivity, permission: String) {
                                        process(serviceMethod)
                                    }
                                })
                    } else {
                        process(serviceMethod)
                    }
                }
            })
        } else if (!serviceMethod.getFailurePermissionList().isEmpty()) {
            val failureHandler = serviceMethod.permissionFailureHandler
            failureHandler?.onFailed(serviceMethod.getFailurePermissionList(), act, serviceMethod.method)
        } else {
            serviceMethod.callback()
        }
    }
}
