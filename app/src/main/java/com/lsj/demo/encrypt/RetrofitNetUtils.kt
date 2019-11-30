package com.lsj.demo.encrypt

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import rx.Observable
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.http.Query


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

    @GET("/shortUrl/createShortUrl.json")
    fun getShortUrl(@Query("sourceUrl") sourceUrl: String,
                    @Query("bizType") bizType: String): Observable<ShortUrl>
}


