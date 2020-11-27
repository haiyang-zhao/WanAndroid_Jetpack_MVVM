package com.zhy.jetpack.mvvm.network

import com.zhy.jetpack.mvvm.livedata.UnPeekLiveData

class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}