package com.lsj.demo

import android.app.Application
/**
 * @CreateDate: 2019-10-19
 * @Version: 1.0.0
 */
class DemoApp: Application(){

    override fun onCreate() {
        super.onCreate()
        CxtUtils.setContext(this)
    }
}