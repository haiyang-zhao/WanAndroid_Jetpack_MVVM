package com.zhy.jetpack.mvvm.http

import android.os.Build
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

abstract class BaseApiFactory(private val baseUrl: BaseUrl) {

    private val log = LoggerFactory.getLogger(BaseApiFactory::class.java)

    private fun getClientBuilder(): OkHttpClient.Builder {
        val clientBuilder = OkHttpClient.Builder().followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return enableTls12OnPreLollipop(clientBuilder)
    }


    //Android4.x系统支持TLS1.2的解决方案
    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT in 16..21) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))
                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build()
                val specs: MutableList<ConnectionSpec> = ArrayList()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)
                client.connectionSpecs(specs)
            } catch (ex: Exception) {
                log.error("tls enabled error", ex)
            }
        }
        return client
    }

    private val okHttpClient: OkHttpClient
        get() {
            return setHttpClientBuilder(getClientBuilder()).build()
        }


    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    abstract fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，Protocol
     */
    abstract fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    fun <T> create(interfaceType: Class<T>): T {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl.url())
            .client(okHttpClient)
        return setRetrofitBuilder(builder).build().create(interfaceType)
    }


}