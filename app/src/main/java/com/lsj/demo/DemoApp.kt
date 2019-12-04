package com.lsj.demo

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @CreateDate: 2019-10-19
 * @Version: 1.0.0
 */
class DemoApp: Application(){

    override fun onCreate() {
        super.onCreate()
        CxtUtils.setContext(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}