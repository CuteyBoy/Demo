package com.lsj.demo.encrypt

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.lsj.demo.R
import kotlinx.android.synthetic.main.activity_encrypt_test.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URLEncoder

/**
 * @CreateDate: 2019-11-30
 * @Version: 1.0.0
 * a [longArrayOf]
 */
class EncryptTestActivity: FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt_test)
        val keyPair = RSAUtils.generatorKeyPair()

        clickBtn.setOnClickListener {
            requestData(keyPair)
        }

    }

    private fun requestData(keyPair: Pair<String, String>) {
        val encodeText = "https://www.baidu.com"
        val startTime = System.currentTimeMillis()
        val encodeTextRSA = RSAUtils.encode(encodeText, keyPair.first)
        val sourceUrlEncode = URLEncoder.encode(encodeTextRSA, "UTF-8")
        val baseUrl = "https://mtx.yj.ink"
        RetrofitNetUtils.getServiceApi(baseUrl, ServiceAPI::class.java)
            .getShortUrl(sourceUrlEncode, "item")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val endTime = System.currentTimeMillis()
                showText.text = it.toString().plus("  hao time=${endTime - startTime}")
            }, {
                showText.text = it.message ?: ""
            }, {

            })
    }
}

data class ShortUrl(
    var currentTimeMs: Long = 0,
    var data: Any? = null,
    var errorCode: Int = -1,
    var errorMessage: String? = null
)