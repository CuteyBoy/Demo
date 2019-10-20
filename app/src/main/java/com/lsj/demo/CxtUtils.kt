package com.lsj.demo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import java.lang.ref.WeakReference

/**
 * @CreateDate: 2019-10-19
 * @Version: 1.0.0
 */
object CxtUtils{

    private var mWeakContext: WeakReference<Context>? = null

    @JvmStatic
    fun getContext(): Context? {
        return mWeakContext?.get()
    }

    @JvmStatic
    fun setContext(context: Context) {
        mWeakContext = WeakReference(context)
    }

    @JvmStatic
    fun getDensity(): Float {
        return getContext()?.resources?.displayMetrics?.density ?: 0f
    }

    @JvmStatic
    fun getColor(colorResId: Int): Int {
        val context = getContext()
        return if(context != null) {
            ContextCompat.getColor(context, colorResId)
        } else {
            Color.BLACK
        }
    }

    @JvmStatic
    fun getDrawable(iconResId: Int): Drawable {
        val context = getContext()
        return if(context != null) {
            ContextCompat.getDrawable(context, iconResId) ?: ColorDrawable(Color.TRANSPARENT)
        } else {
            ColorDrawable(Color.TRANSPARENT)
        }
    }
}