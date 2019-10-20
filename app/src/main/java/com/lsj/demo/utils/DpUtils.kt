package com.lsj.demo.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * @CreateDate: 2019-10-19
 * @Version: 1.0.0
 */
object DpUtils {
    /*** 获取手机屏幕宽度 */
    @JvmStatic
    fun getScreenWidth(context: Context?): Int {
        return context?.resources?.displayMetrics?.widthPixels
            ?: Resources.getSystem().displayMetrics.widthPixels
    }


    /*** 获取手机屏幕高度 */
    @JvmStatic
    fun getScreenHeight(context: Context?): Int {
        return context?.resources?.displayMetrics?.heightPixels
            ?: Resources.getSystem().displayMetrics.heightPixels
    }

    @JvmStatic
    fun getWidthPixels(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    @JvmStatic
    fun getHeightPixels(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    @JvmStatic
    fun sp2px(spValue: Float): Float {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return spValue * fontScale + 0.5f
    }

    /**
     * Value of px to value of dp.
     *
     * @param pxValue The value of px.
     * @return value of dp
     */
    @JvmStatic
    fun px2dp(pxValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * Value of px to value of sp.
     *
     * @param pxValue The value of px.
     * @return value of sp
     */
    @JvmStatic
    fun px2sp(pxValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * Converts an unpacked complex data value holding a dimension to its final floating
     * point value. The two parameters <var>unit</var> and <var>value</var>
     * are as in [TypedValue.TYPE_DIMENSION].
     *
     * @param value The value to apply the unit to.
     * @param unit  The unit to convert from.
     * @return The complex floating point value multiplied by the appropriate
     * metrics depending on its unit.
     */
    @JvmStatic
    fun applyDimension(value: Float, unit: Int): Float {
        val metrics = Resources.getSystem().displayMetrics
        when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
        }
        return 0f
    }
}