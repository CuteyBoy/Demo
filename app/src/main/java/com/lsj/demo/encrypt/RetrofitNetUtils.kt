package com.lsj.demo.encrypt

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.http.*


/**
 * @CreateDate: 2019-11-30
 * @Version: 1.0.0
 */
object RetrofitNetUtils {

    /**
     * 创建retrofit对象
     * @param baseUrl 请求地址
     * @return 返回Retrofit实例
     */
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .setLenient()
                .create()))
            .build()
    }

    /**
     * 根据即地址以及服务接口返回接口实例
     * @param baseUrl 请求基础地址
     * @param serviceApiCls 服务api接口的class
     * @return 返回接口实例
     */
    fun <T> getServiceApi(baseUrl: String, serviceApiCls: Class<T>): T {
        return createRetrofit(baseUrl).create(serviceApiCls)
    }
}


/**
 * 与后台对应的服务api接口
 */
interface ServiceAPI {

    @GET("encrypt/getEncryptDataCase1")
    @Headers("ticket:ticket%7C14896_c9c6e0d63d178ebd01b1aa0978df6288v")
    fun getEncryptDataCase1(@Query("sourceUrl") sourceUrl: String,
                    @Query("ticket") ticket: String): Observable<String>

    @GET("encrypt/getEncryptDataCase2")
    fun getEncryptDataCase2(@Query("content") content: String): Observable<String>

    @GET("encrypt/getEncryptDataCase3")
    fun getEncryptDataCase3(@Query("content") content: String): Observable<String>
}


