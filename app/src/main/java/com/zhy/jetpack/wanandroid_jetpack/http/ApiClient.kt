package com.zhy.jetpack.wanandroid_jetpack.http

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.zhy.jetpack.mvvm.http.BaseApiFactory
import com.zhy.jetpack.mvvm.http.BaseUrl
import com.zhy.jetpack.wanandroid_jetpack.AppContext
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


val apiClient: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    ApiFactory.instance.create(ApiService::class.java)
}

class ApiFactory(baseUrl: BaseUrl) : BaseApiFactory(baseUrl) {

    companion object {
        val instance: ApiFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiFactory(baseUrl)
        }
    }

    private val log = LoggerFactory.getLogger(ApiFactory::class.java)
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor { message -> Log.d("ApiFactory", message) }
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(AppContext.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(MyHeadInterceptor())
            addNetworkInterceptor(StethoInterceptor())
            // 日志拦截器
            addInterceptor(logging)
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext))
    }
}


private val baseUrl = object : BaseUrl {
    override fun url(): HttpUrl {
        return HttpUrl.parse(BASE_URL)!!
    }

}