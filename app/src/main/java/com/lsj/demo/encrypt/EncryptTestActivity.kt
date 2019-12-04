package com.lsj.demo.encrypt

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.lsj.demo.R
import com.lsj.demo.encrypt.bo.ItemBo
import com.lsj.random.bo.JsonParamBo
import com.lsj.random.generator.ConfigBuilder
import com.lsj.random.generator.RandomDataUtils
import com.lsj.random.generator.TroubleGenerator
import com.lsj.random.utils.BoToJsonUtils
import kotlinx.android.synthetic.main.activity_encrypt_test.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * @CreateDate: 2019-11-30
 * @Version: 1.0.0
 * a [longArrayOf]
 */
class EncryptTestActivity: FragmentActivity(){

    private val baseUrl = "http://10.3.6.214:8080/trouble/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt_test)
        val keyPair = RSAUtils.generatorKeyPair()

        clickBtn.setOnClickListener {
            encryptParams1(keyPair)
        }
    }

    private fun requestData() {
        val startTime = System.currentTimeMillis()
        var sourceUrl = "https://search.yunjiglobal.com/indexsearchweb/indexsearch/item/searchItem.json?whetherSuggest=1&sumCondition=1&lookStock=2&searchType=1&pageIndex=0&name=11&pageSize=20&source=1&userType=0&deviceId=213ED04079B3DEDB5E3CF41A7A3A6377&reqId=1575374426553&strVersion=3.68.66259&appCont=1&ticket=ticket%7C14896_c9c6e0d63d178ebd01b1aa0978df6288v"
        sourceUrl = URLEncoder.encode(sourceUrl, "utf-8")
        val ticket = "ticket%7C14896_c9c6e0d63d178ebd01b1aa0978df6288v"
        RetrofitNetUtils
            .getServiceApi(baseUrl, ServiceAPI::class.java)
            .getEncryptDataCase1(sourceUrl, ticket)
            .subscribeOn(Schedulers.io())
            .map {
                val decodeContent = RSAUtils.decodeText(it)
                decodeContent
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val endTime = System.currentTimeMillis()
                println("----timeConsuming=${endTime - startTime}")
                showText.text = it
            }, {
                showText.text = it.message ?: ""
            }, {
            })
    }

    private fun requestData2() {
        val startTime = System.currentTimeMillis()
        var content = "99999999999900000000000000000密码大苏打似的为为内部死的似的死的似的收到收到"
        content = RSAUtils.encodeText(content)
        content = URLEncoder.encode(content, "utf-8")
        RetrofitNetUtils
            .getServiceApi(baseUrl, ServiceAPI::class.java)
            .getEncryptDataCase2(content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val endTime = System.currentTimeMillis()
                println("----timeConsuming=${endTime - startTime}")
                showText.text = it
            }, {
                showText.text = it.message ?: ""
            }, {
            })
    }

    private fun requestData3() {
        val startTime = System.currentTimeMillis()
        var content = "99999999999900000000000000000密码大苏打似的为为内部死的似的死的似的收到收到"
        content = RSAUtils.encodeText(content)
        content = URLEncoder.encode(content, "utf-8")
        RetrofitNetUtils
            .getServiceApi(baseUrl, ServiceAPI::class.java)
            .getEncryptDataCase3(content)
            .subscribeOn(Schedulers.io())
            .map {
                RSAUtils.decodeText(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val endTime = System.currentTimeMillis()
                println("----timeConsuming=${endTime - startTime}")
                showText.text = it
            }, {
                showText.text = it.message ?: ""
            }, {
            })
    }

    private inline fun checkTimeConsuming(testName: String, crossinline callback: ()-> String) {
        Observable.create<Any> {
            val startTime = System.currentTimeMillis()
            val result = callback.invoke()
            val timeConsuming = System.currentTimeMillis() - startTime
            println("$testName---- timeConsuming=$timeConsuming")
            it.onNext(result)
            it.onCompleted()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                println("加密成功")
            }, {
                println(it.message)
            }, {})
    }

    /**
     * DES: 单纯测试只有1-10参数个组装耗时情况
     **/
    private fun encryptParams1(keyPair: Pair<String, String>) {
        // DES: 检测只有两三个参数的耗时情况
        val params = hashMapOf<String, String>()
        checkTimeConsuming("shortUrlParams"){
            params["type"] = "1"
            params["content"] = "东风国际华蓝湖"
            params["url"] = "https://www.baidu.com"
            params["count"] = "10000"
            params["title"] = "时令鲜果,云集热销榜,云集超市,云鸡农场,服饰城,必买好货,品制500,i尚直播,云集国际,云集健康"
            params["recommend"] = "俄罗斯进口紫皮糖2袋装"
            params["activityName"] = "俄罗斯进口 Kpokaht扁桃仁夹心代可可脂巧克力紫皮糖 500gx2"
            params["fineImg"] = "http:\\/\\/image.yunjiglobal.com\\/20191023155250504426.jpg"
            params["isClearGoods"] = "0"
            params["currentTime"] = "1575342301058"
            params.toString()
        }
        checkTimeConsuming("encryptShortUrlParams"){
            params.keys.forEach {
                params[it] =  RSAUtils.encode(params[it]!!, keyPair.first)
            }
            params.toString()
        }
        val itemJsonData = BoToJsonUtils.toJson(ItemBo::class.java)
        checkTimeConsuming("bigDataParams") {
            RSAUtils.encode(itemJsonData, keyPair.first)
        }
        val encodeStr = RSAUtils.encode(itemJsonData, keyPair.first)
        checkTimeConsuming("bigDecodeDataParams") {
            RSAUtils.decoded(encodeStr, keyPair.second)
        }
    }

}