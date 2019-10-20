package com.lsj.demo.customview

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.lsj.demo.R
import kotlinx.android.synthetic.main.activity_view_location.*

/**
 * @Description: 用于演示视图位置信息
 * @CreateDate: 2019-10-13
 * @Version: 1.0.0
 */
class ViewLocationInfoActivity: FragmentActivity(){

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_location)
        viewInfoTv.post {
            viewInfoTv.text = "top=${viewInfoTv.top} left=${viewInfoTv.left} \n " +
                    "bottom=${viewInfoTv.bottom} right=${viewInfoTv.right}" +
                    " width=${viewInfoTv.right - viewInfoTv.left} " +
                    "height=${viewInfoTv.bottom - viewInfoTv.top} " +
                    "width==${viewInfoTv.width} " +
                    "height==${viewInfoTv.height}"
        }

//        val x = Vi˜ewConfiguration.get(this).scaledTouchSlop
//        viewInfoTv.text = "x = $x"
    }

    @SuppressLint("SetTextI18n")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN) {
            viewInfoTv.text = "x= ${event.x} y=${event.y} rawX=${event.rawX} rawY=${event.rawY}"
        }
        return super.onTouchEvent(event)
    }
}