package com.zhy.jetpack.mvvm.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 在发送一次后在将AtomicBoolean设置为false，阻止后续前台重新触发时的数据发送。
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending by lazy {
        AtomicBoolean(false)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }


    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        this.value = null
    }
}