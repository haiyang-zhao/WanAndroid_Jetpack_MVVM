package com.zhy.jetpack.mvvm.ext

import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
fun <T> getClazz(obj: Any): T {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as T
}