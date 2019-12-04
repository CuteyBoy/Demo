package com.lsj.demo.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView

/**
 * @Description: 用于显示销量，好评率超出指定宽度则截断显示
 * @CreateDate: 2019/11/5 16:22
 * @Version: 1.0.0
 */
class OverflowView @JvmOverloads constructor(context: Context,
                   attrs: AttributeSet? = null,
                   defaultStyle: Int = 0): TextView(context, attrs, defaultStyle){

    /** DES: 前缀内容部分 */
    private var mPrefixContent: String? = null
    /** DES: 整体内容部分 */
    private var mNormalContent: String? = null
    /** DES: 用于保存测量的宽度 */
    private var mMeasureTextWidth = 0f
    /** DES: 用来判断是否替换成 */
    private var isReplaceFinish = false

    override fun onDraw(canvas: Canvas?) {
        if(measuredWidth.compareTo(mMeasureTextWidth) <= 0 && !isReplaceFinish) {
            isReplaceFinish = true
            text = mPrefixContent ?: ""
            return
        }
        super.onDraw(canvas)
    }
    
    /**
     * DES: 设置显示文本内容
     * @param prefixContent 前缀显示内容
     * @param normalContent 整体显示内容
     **/
    fun setText(prefixContent: String?, normalContent: String?) {
        isReplaceFinish = false
        mPrefixContent = prefixContent
        mNormalContent = normalContent ?: ""
        mMeasureTextWidth = paint?.measureText(mNormalContent) ?: 0f
        text = mNormalContent
    }
}