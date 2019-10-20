package com.lsj.demo.utils

import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import com.lsj.demo.CxtUtils

/**
 * @Description: 用于封装一些常用的对视图进行修改的方法
 *      比如边距，宽度，边缘图标等等
 *      1.修改边距：有两个主要的方法，一个是普通支持dp和px单位的setMargin()方法；
 *      另一个是支持按照设计比列进行设置.
 *      则直接调用setTop(left、right、bottom)Margin()方法，或者setDesignTop(left、right、bottom)Margin()方法。
 *      2.修改视图宽高：也有两个主要的方法，一个是支持普通dp和px单位的setSize()方法；
 *      另个是支持按照设计比列进行设置的setDesignSize()方法。如果需要单独设置宽高，
 *      则使用setWidth()、setHeight()方法或者setDesignWidth()、setDesignHeight()方法。
 *      3.修改TextView以及TextView子类的边缘图标：可以直接调用setBoundsDrawable()或者setBoundsDrawableById()方法来设置，也可以
 *      单独调用setLeft(top、right、bottom)Drawable()方法。
 *      4.设置视图的点击监听器，该点击监听器是仿连点击，setOnClickListener()方法。
 *      5.观察视图改变监听器，调用afterMeasured()方法。
 *      6.适配某个视图的大小及边距，调用adapterViewSize()或者adapterLayoutSize()方法。
 *      7.获取设计搞相对于本机的实际比例大小，调用getAdapterSize()方法。
 *      8.设置视图的可见还是不可见，调用setVisibility()方法。
 * @CreateDate: 2019/4/11 10:47
 * @Version: 1.0.0
 */
class ViewModifyUtils private constructor() {

    companion object {

        /** DES: 防止连续点击间隔时间 */
        private const val DEFAULT_CLICK_SECOND = 1
        /** DES: 默认设计稿宽度375 */
        const val DEFAULT_DESIGN_WIDTH = 375
        /** DES: 默认设计稿高度667 */
        const val DEFAULT_DESIGN_HEIGHT = 667
        /** DES: 无效边界值 */
        const val INVALID_VALUE = -10000

        /**
         * DES: 改变布局容器的宽高以及边距的比列大小，
         * 该方法在被调用后不会主动刷新界面，若需要主动刷新传isImmediateRefresh为true
         * 并且该方法会默认才有设计搞屏幕宽度375进行自动适配大小
         * 注意：该方法适合在刷新界面不高的地方进行使用
         * AUTHOR: Robot
         * TIME: 2019/4/11 19:33
         *
         * @param targetView 目标视图容器或者视图
         * @param designWidth 设计稿屏幕宽度
         * @param isImmediateRefresh 是否立即刷新，默认是false
         * @param designHeight 设计屏幕高度，默认是无效值
         **/
        @JvmStatic
        @JvmOverloads
        fun adapterLayoutSize(targetView: View?,
                              isImmediateRefresh: Boolean = false,
                              designWidth: Int = DEFAULT_DESIGN_WIDTH,
                              designHeight: Int = INVALID_VALUE) {
            if(targetView is ViewGroup) {
                adapterLayoutSize(targetView, isImmediateRefresh, designWidth, designHeight)
            } else {
                adapterViewSize(targetView, isImmediateRefresh, designWidth, designHeight)
            }
        }

        /**
         * DES: 改变布局容器的宽高以及边距的比列大小，
         * 该方法在被调用后不会主动刷新界面，若需要主动刷新传isImmediateRefresh为true
         * 并且该方法会默认才有设计搞屏幕宽度375进行自动适配大小
         * 注意：该方法适合在刷新界面不高的地方进行使用
         * 注意：该方法适合在刷新界面不高的地方进行使用
         * TIME: 2019/4/11 19:33
         *
         * @param targetView 目标视图容器
         * @param designWidth 设计稿屏幕宽度
         * @param isImmediateRefresh 是否立即刷新
         * @param designHeight 设计屏幕高度，默认是无效值
         **/
        @JvmStatic
        @JvmOverloads
        fun adapterLayoutSize(targetView: ViewGroup?,
                              isImmediateRefresh: Boolean = false,
                              designWidth: Int = DEFAULT_DESIGN_WIDTH,
                              designHeight: Int = INVALID_VALUE) {
            //如果传入是视图为空则直接不进行大小适配
            if(targetView == null) {
                return
            }
            //对于是ViewGroup类型的先自调整
            adapterViewSize(targetView, isImmediateRefresh, designWidth, designHeight)
            //开始容器内部视图按照视图树进行自动大小适配计算
            for (index in 0..targetView.childCount) {
                adapterLayoutSize(targetView.getChildAt(index), isImmediateRefresh, designWidth, designHeight)
            }
        }

        /**
         * DES: 改变布局容器的宽高以及边距的比列大小，
         * 该方法在被调用后不会主动刷新界面，若需要主动刷新传isImmediateRefresh为true
         * 并且该方法会默认才有设计搞屏幕宽度375进行自动适配大小
         * 注意：该方法适合在刷新界面不高的地方进行使用
         * TIME: 2019/4/11 19:33
         *
         * @param targetView 目标视图
         * @param designWidth 设计稿屏幕宽度
         * @param isImmediateRefresh 是否立即刷新, 默认是false
         * @param designHeight 设计屏幕高度，默认是INVALID_VALUE
         **/
        private fun adapterViewSize(targetView: View?,
                            isImmediateRefresh: Boolean = false,
                            designWidth: Int = DEFAULT_DESIGN_WIDTH,
                            designHeight: Int = INVALID_VALUE) {
            if (targetView?.layoutParams is ViewGroup.MarginLayoutParams) {
                val param = targetView.layoutParams as ViewGroup.MarginLayoutParams
                if (param.width > 0) {
                    //排除宽度为MATCH_PARENT 和 WRAP_CONTENT 以及0的情况
                    param.width = getAdapterSizeByPxValue(param.width, designWidth, designHeight)
                }
                if (param.height > 0) {
                    //排除height为MATCH_PARENT 和 WRAP_CONTENT 以及0的情况
                    param.height = getAdapterSizeByPxValue(param.height, designWidth, designHeight)
                }
                if (param.leftMargin != 0) {
                    //排除leftMargin为0的情况
                    param.leftMargin = getAdapterSizeByPxValue(param.leftMargin, designWidth, designHeight)
                }
                if (param.topMargin != 0) {
                    //排除topMargin为0的情况
                    param.topMargin = getAdapterSizeByPxValue(param.topMargin, designWidth, designHeight)
                }
                if (param.bottomMargin != 0) {
                    //排除topMargin为0的情况
                    param.bottomMargin = getAdapterSizeByPxValue(param.bottomMargin, designWidth, designHeight)
                }
                if (param.rightMargin != 0) {
                    //排除rightMargin为0的情况
                    param.rightMargin = getAdapterSizeByPxValue(param.rightMargin, designWidth, designHeight)
                }
                if (isImmediateRefresh) {
                    targetView.layoutParams = param
                }
            }
        }

        /**
         * DES: 根据宽高自动计算适配大小值, 传入的值为像素值
         * TIME: 2019/4/12 14:50
         *
         * @param pxValue 像素值
         * @param designWidth 设计屏幕宽度，默认是DEFAULT_DESIGN_WIDTH
         * @param designHeight 设计屏幕高度，默认是INVALID_VALUE
         * @return 返回条件
         **/
        private fun getAdapterSizeByPxValue(pxValue: Int, designWidth: Int = DEFAULT_DESIGN_WIDTH, designHeight: Int = INVALID_VALUE): Int {
            val dpValue = DpUtils.px2dp(pxValue.toFloat())
            return getAdapterSize(dpValue, designWidth, designHeight)
        }

        /**
         * DES: 根据宽高自动计算适配大小值,
         * 如果传了设计宽度和高度，则优先使用宽度进行适配，
         * 如果没有传则使用默认设计屏幕宽度来进行计算。
         * TIME: 2019/4/12 14:50
         *
         * @param dpValue dp值
         * @param designWidth 设计屏幕宽度，默认是DEFAULT_DESIGN_WIDTH
         * @param designHeight 设计屏幕高度，默认是INVALID_VALUE
         * @return 返回条件
         **/
        @JvmStatic
        @JvmOverloads
        fun getAdapterSize(dpValue: Int,
                           designWidth: Int = DEFAULT_DESIGN_WIDTH,
                           designHeight: Int = INVALID_VALUE): Int{
            return when {
                //采用设计屏幕宽度进行比例计算
                designWidth != INVALID_VALUE -> getTransformSize(dpValue, designWidth, INVALID_VALUE)
                //采用设计屏幕高度进行比例计算
                designHeight != INVALID_VALUE -> getTransformSize(dpValue, INVALID_VALUE, designHeight)
                //默认采用设计屏幕宽度继续计算
                else -> getTransformSize(dpValue, DEFAULT_DESIGN_WIDTH, INVALID_VALUE)
            }
        }

        /**
         * DES: 设置目标视图是否可见
         * TIME: 2019/4/11 19:04
         *
         * @param targetView 目标视图
         * @param visibility 传值，如View.VISIBLE, View.GONE, View.INVISIBLE, 默认是View.GONE
         * @return
         **/
        @JvmStatic
        @JvmOverloads
        fun setVisibility(targetView: View?, visibility: Int = View.GONE) {
            // DES: 如果不相等则设置，防止过度
            if(targetView?.visibility ?: View.GONE != visibility) {
                targetView?.visibility = visibility
            }
        }

        /**
         * DES: 该方法在视图测绘成功后通过回调函数返回
         * 用来动态获取一些视图的大小
         * TIME: 2019/4/11 18:59
         * @param targetView 目标视图
         * @param callbackFunc 回调接口参数
         **/
        @JvmStatic
        fun afterMeasured(targetView: View?, callbackFunc: (view: View) -> Unit) {
            targetView?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
                override fun onGlobalLayout() {
                    //移除全局监听器
                    targetView.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    //执行回调函数
                    callbackFunc(targetView)
                }
            })
        }

        /**
         * DES: 设置目标视图的边缘图标
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param leftDrawable 左边图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setLeftDrawable(targetView: TextView?,
                            leftDrawable: Drawable?,
                            drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawable(targetView,
                    leftDrawable, null,
                    null, null,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param leftDrawable 左边图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setLeftDrawableById(targetView: TextView?,
                                leftDrawable: Int,
                                drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawableById(targetView,
                    leftDrawable, 0,
                    0, 0,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param topDrawable 顶部图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setTopDrawableById(targetView: TextView?,
                               topDrawable: Int,
                               drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawableById(targetView,
                    0, topDrawable,
                    0,0,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param topDrawable 顶部图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setTopDrawable(targetView: TextView?,
                           topDrawable: Drawable?,
                           drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawable(targetView,
                    null, topDrawable,
                    null,null,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param rightDrawable 右边图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setRightDrawable(targetView: TextView?,
                             rightDrawable: Drawable?,
                             drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawable(targetView,
                    null, null,
                    rightDrawable,null,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param rightDrawable 右边图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setRightDrawableById(targetView: TextView?,
                                 rightDrawable: Int,
                                 drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawableById(targetView,
                    0,0,
                    rightDrawable,0,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param bottomDrawable 底部图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setBottomDrawable(targetView: TextView?,
                              bottomDrawable: Drawable?,
                              drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawable(targetView,
                    null, null,
                    null, bottomDrawable,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param bottomDrawable 底部图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setBottomDrawableById(targetView: TextView?,
                                  bottomDrawable: Int,
                                  drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawableById(targetView,
                    0, 0,
                    0,bottomDrawable,
                    drawablePadding = drawablePadding)
        }

        /**
         * DES: 设置目标视图的边缘图标
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param leftDrawable 左边图标
         * @param topDrawable 顶部图标
         * @param rightDrawable 右边图标
         * @param bottomDrawable 底部图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setBoundsDrawableById(targetView: TextView?,
                                  leftDrawable: Int,
                                  topDrawable: Int,
                                  rightDrawable: Int,
                                  bottomDrawable: Int,
                                  drawablePadding: Int = INVALID_VALUE) {
            setBoundsDrawable(targetView,
                    getDrawable(targetView, leftDrawable),
                    getDrawable(targetView, topDrawable),
                    getDrawable(targetView, rightDrawable),
                    getDrawable(targetView, bottomDrawable),
                    drawablePadding)
        }

        /**
         * DES: 根据图标id，获取图标实例
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:24
         *
         * @param targetView 目标视图
         * @param drawableResId 图标资源Id
         * @return 返回图标实例
         **/
        private fun getDrawable(targetView: TextView?,
                                drawableResId: Int): Drawable? {
            return if (drawableResId > 0 && targetView != null) {
                ContextCompat.getDrawable(targetView.context, drawableResId)
            } else {
                null
            }
        }

        /**
         * DES: 设置目标视图的边缘图标
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:12
         *
         * @param targetView 目标视图
         * @param leftDrawable 左边图标
         * @param topDrawable 顶部图标
         * @param rightDrawable 右边图标
         * @param bottomDrawable 底部图标
         * @param drawablePadding 图标距离文本的边距，单位是dp
         **/
        @JvmStatic
        @JvmOverloads
        fun setBoundsDrawable(targetView: TextView?,
                              leftDrawable: Drawable?,
                              topDrawable: Drawable?,
                              rightDrawable: Drawable?,
                              bottomDrawable: Drawable?,
                              drawablePadding: Int = INVALID_VALUE) {
            //高版本直接调用现成的方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                targetView?.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, topDrawable, rightDrawable, bottomDrawable)
            } else {
                //低版本单独处理
                leftDrawable?.setBounds(0, 0,
                        leftDrawable.minimumWidth, leftDrawable.minimumHeight)
                topDrawable?.setBounds(0, 0,
                        topDrawable.minimumWidth, topDrawable.minimumHeight)
                rightDrawable?.setBounds(0, 0,
                        rightDrawable.minimumWidth, rightDrawable.minimumHeight)
                bottomDrawable?.setBounds(0, 0,
                        bottomDrawable.minimumWidth, bottomDrawable.minimumHeight)
                targetView?.setCompoundDrawables(leftDrawable,
                        topDrawable, rightDrawable, bottomDrawable)
            }
            //判断是否是有效的边距值
            if (drawablePadding != INVALID_VALUE) {
                //设置图标到文本的距离
                targetView?.compoundDrawablePadding = getTransformValue(drawablePadding, true)
            }
        }

        /**
         * DES: 清除目标视图的边缘图标
         * AUTHOR: Robot
         * TIME: 2019/4/11 17:12
         **/
        @JvmStatic
        fun clearTextViewBoundsDrawable(targetView: TextView?) {
            setBoundsDrawable(targetView, null, null, null, null, 0)
        }

        /**
         * DES: 设置目标视图设计稿相对于当前屏幕的高
         * AUTHOR: Robot
         * TIME: 2019/4/11 16:33
         *
         * @param targetView 目标视图
         * @param height 高度值
         * @param designHeight 设计稿屏幕高度
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignHeight(targetView: View?,
                            height: Int,
                            isImmediateRefresh: Boolean = true,
                            designWidth: Int = DEFAULT_DESIGN_WIDTH,
                            designHeight: Int = INVALID_VALUE) {
            setDesignSize(targetView,
                    INVALID_VALUE,
                    height = height,
                    isImmediateRefresh = isImmediateRefresh,
                    designWidth = designWidth,
                    designHeight = designHeight)
        }

        /**
         * DES: 设置目标视图设计稿相对于当前屏幕的宽
         * AUTHOR: Robot
         * TIME: 2019/4/11 16:33
         *
         * @param targetView 目标视图
         * @param width 宽度值
         * @param designHeight 设计稿屏幕高度
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignWidth(targetView: View?,
                           width: Int,
                           isImmediateRefresh: Boolean = true,
                           designWidth: Int = DEFAULT_DESIGN_WIDTH,
                           designHeight: Int = INVALID_VALUE) {
            setDesignSize(targetView,
                    width,
                    height = INVALID_VALUE,
                    isImmediateRefresh = isImmediateRefresh,
                    designWidth = designWidth,
                    designHeight = designHeight)
        }

        /**
         * DES: 设置目标视图设计稿相对于当前屏幕的宽高
         * AUTHOR: Robot
         * TIME: 2019/4/11 16:33
         *
         * @param targetView 目标视图
         * @param width 宽度值
         * @param height 高度值
         * @param designWidth 设计稿屏幕宽度
         * @param designHeight 设计稿屏幕高度
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignSize(targetView: View?,
                          width: Int,
                          height: Int,
                          isImmediateRefresh: Boolean = true,
                          designWidth: Int = DEFAULT_DESIGN_WIDTH,
                          designHeight: Int = DEFAULT_DESIGN_HEIGHT) {

            setSize(targetView,
                    //重新计算宽度
                    getAdapterSize(width, designWidth, designHeight),
                    //重新计算高度
                    getAdapterSize(height, designWidth, designHeight),
                    false,
                    isImmediateRefresh)
        }

        /**
         * DES: 设置目标视图的宽
         * AUTHOR: Robot
         * TIME: 2019/4/11 16:33
         *
         * @param targetView 目标视图
         * @param width 宽度值
         * @param isDpValue 是否dp值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setWidth(targetView: View?,
                     width: Int,
                     isDpValue: Boolean = true,
                     isImmediateRefresh: Boolean = true) {
            setSize(targetView,
                    width,
                    height = INVALID_VALUE,
                    isDpValue = isDpValue,
                    isImmediateRefresh = isImmediateRefresh)
        }

        /**
         * DES: 设置目标视图的高
         * AUTHOR: Robot
         * TIME: 2019/4/11 16:33
         *
         * @param targetView 目标视图
         * @param height 高度值
         * @param isDpValue 是否dp值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setHeight(targetView: View?,
                      height: Int,
                      isDpValue: Boolean = true,
                      isImmediateRefresh: Boolean = true) {
            setSize(targetView,
                    INVALID_VALUE,
                    height = height,
                    isDpValue = isDpValue,
                    isImmediateRefresh = isImmediateRefresh)
        }

        /**
         * DES: 设置目标视图的宽高
         * AUTHOR: Robot
         * TIME: 2019/4/11 16:33
         *
         * @param targetView 目标视图
         * @param width 宽度值,单位是px，不需要设置可传INVALID_VALUE
         * @param height 高度值,单位是px，不需要设置可传INVALID_VALUE
         * @param isDpValue 是否dp值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setSize(targetView: View?,
                    width: Int,
                    height: Int,
                    isDpValue: Boolean = true,
                    isImmediateRefresh: Boolean = true) {
            //获取目标视图布局参数，如果为空则直接返回
            val layoutParam = targetView?.layoutParams ?: return
            //用来判断值是否发生改变，有改变则更新
            //否则不更新，防止多次刷新，造成的性能问题
            var isChangeValue = false
            //保存新值元组对，包含单个值是否改变以及新值
            var newValuePair: Pair<Boolean, Int>
            //如果width不等于无效值则进行计算
            if (width != INVALID_VALUE) {
                //获取width的新计算的值
                newValuePair = getPairValue(layoutParam.width, width, isDpValue)
                //判断值是否发生改变
                if (newValuePair.first) {
                    isChangeValue = true
                    //如果有发生变化则替换
                    layoutParam.width = newValuePair.second
                }
            }
            //如果height不等于无效值则进行计算
            if (height != INVALID_VALUE) {
                //获取height的新计算的值
                newValuePair = getPairValue(layoutParam.height, height, isDpValue)
                //判断值是否发生改变
                if (newValuePair.first) {
                    isChangeValue = true
                    //如果有发生变化则替换
                    layoutParam.height = newValuePair.second
                }
            }
            if (isImmediateRefresh && isChangeValue) {
                //如果值有改变进行重新设置
                targetView.layoutParams = layoutParam
            }
        }

        /**
         * DES: 给目标视图设置采用设计稿上的宽或者
         *  高来（默认是采用宽度）重新计算当前屏幕上的边距值, 调用该方法会立即进行刷新
         * AUTHOR: Robot
         * TIME: 2019/4/11 14:17
         *
         * @param targetView 目标视图
         * @param leftMargin 左边边距值，具体参考设计稿原值
         * @param designWidth 设计稿上的宽度，具体参考设计稿原值
         * @param isImmediateRefresh true表示立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignLeftMargin(targetView: View?,
                                leftMargin: Int,
                                isImmediateRefresh: Boolean = true,
                                designWidth: Int = DEFAULT_DESIGN_WIDTH,
                                designHeight: Int = INVALID_VALUE) {
            setDesignMargin(
                    targetView,
                    leftMargin,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    isImmediateRefresh = isImmediateRefresh,
                    designWidth = designWidth,
                    designHeight = designHeight)
        }

        /**
         * DES: 给目标视图设置采用设计稿上的宽或者
         *  高来（默认是采用宽度）重新计算当前屏幕上的边距值, 调用该方法会立即进行刷新
         * AUTHOR: Robot
         * TIME: 2019/4/11 14:17
         *
         * @param targetView 目标视图
         * @param topMargin 左边边距值，具体参考设计稿原值
         * @param designWidth 设计稿上的宽度，具体参考设计稿原值
         * @param isImmediateRefresh true表示立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignTopMargin(targetView: View?,
                               topMargin: Int,
                               isImmediateRefresh: Boolean = true,
                               designWidth: Int = DEFAULT_DESIGN_WIDTH,
                               designHeight: Int = INVALID_VALUE) {
            setDesignMargin(
                    targetView,
                    INVALID_VALUE,
                    topMargin,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    isImmediateRefresh = isImmediateRefresh,
                    designWidth = designWidth,
                    designHeight = designHeight)
        }

        /**
         * DES: 给目标视图设置采用设计稿上的宽或者
         *  高来（默认是采用宽度）重新计算当前屏幕上的边距值, 调用该方法会立即进行刷新
         * AUTHOR: Robot
         * TIME: 2019/4/11 14:17
         *
         * @param targetView 目标视图
         * @param rightMargin 左边边距值，具体参考设计稿原值
         * @param designWidth 设计稿上的宽度，具体参考设计稿原值
         * @param isImmediateRefresh true表示立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignRightMargin(targetView: View?,
                                 rightMargin: Int,
                                 isImmediateRefresh: Boolean = true,
                                 designWidth: Int = DEFAULT_DESIGN_WIDTH,
                                 designHeight: Int = INVALID_VALUE) {
            setDesignMargin(
                    targetView,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    rightMargin,
                    INVALID_VALUE,
                    isImmediateRefresh = isImmediateRefresh,
                    designWidth = designWidth,
                    designHeight = designHeight)
        }

        /**
         * DES: 给目标视图设置采用设计稿上的宽或者
         *  高来（默认是采用宽度）重新计算当前屏幕上的边距值, 调用该方法会立即进行刷新
         * AUTHOR: Robot
         * TIME: 2019/4/11 14:17
         *
         * @param targetView 目标视图
         * @param bottomMargin 底部边距值，具体参考设计稿原值
         * @param designWidth 设计稿上的宽度，具体参考设计稿原值
         * @param isImmediateRefresh true表示立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignBottomMargin(targetView: View?,
                                  bottomMargin: Int,
                                  isImmediateRefresh: Boolean = true,
                                  designWidth: Int = DEFAULT_DESIGN_WIDTH,
                                  designHeight: Int = INVALID_VALUE) {
            setDesignMargin(
                    targetView,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    bottomMargin = bottomMargin,
                    isImmediateRefresh = isImmediateRefresh,
                    designWidth = designWidth,
                    designHeight = designHeight)
        }

        /**
         * DES: 给目标视图设置采用设计稿上的宽或者
         *  高来（默认是采用宽度）重新计算当前屏幕上的边距值, 调用该方法会立即进行刷新
         * AUTHOR: Robot
         * TIME: 2019/4/11 14:17
         *
         * @param targetView 目标视图
         * @param leftMargin 左边边距值，具体参考设计稿原值，不需要设置可传INVALID_VALUE
         * @param topMargin 顶部边距值，具体参考设计稿原值,不需要设置可传INVALID_VALUE
         * @param rightMargin 左边边距值，具体参考设计稿原值,不需要设置可传INVALID_VALUE
         * @param bottomMargin 底部边距值，具体参考设计稿原值,不需要设置可传INVALID_VALUE
         * @param designWidth 设计稿上的宽度，具体参考设计稿原值,不需要设置可传INVALID_VALUE
         * @param designHeight 设计稿上的高度，具体参考设计稿原值,不需要设置可传INVALID_VALUE
         * @param isImmediateRefresh true表示立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setDesignMargin(targetView: View?,
                            leftMargin: Int,
                            topMargin: Int,
                            rightMargin: Int,
                            bottomMargin: Int,
                            isImmediateRefresh: Boolean = true,
                            designWidth: Int = DEFAULT_DESIGN_WIDTH,
                            designHeight: Int = INVALID_VALUE) {
            setMargin(targetView,
                    //重新计算左边距
                    getAdapterSize(leftMargin, designWidth, designHeight),
                    //重新计算顶边距
                    getAdapterSize(topMargin, designWidth, designHeight),
                    //重新计算右边距
                    getAdapterSize(rightMargin, designWidth, designHeight),
                    //重新计算底边距
                    getAdapterSize(bottomMargin, designWidth, designHeight),
                    //非dp值
                    false,
                    //是否立即刷新
                    isImmediateRefresh)
        }

        /**
         * DES: 获取设计稿相对于手机屏幕上的值
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:59
         *
         * @param designSize 设计稿上的值
         * @param designHeight 设计稿的宽度
         * @param designWidth 设计稿的高度
         * @return 返回转变的值
         **/
        private fun getTransformSize(designSize: Int,
                                     designWidth: Int = DEFAULT_DESIGN_WIDTH,
                                     designHeight: Int = DEFAULT_DESIGN_HEIGHT): Int {
            return if (designSize == INVALID_VALUE) {
                //如果是否等于无效值直接返回
                return INVALID_VALUE
            } else {
                //判断是按照设计稿宽度还是高度进行计算
                when {
                    designWidth != INVALID_VALUE -> //按照宽度进行计算
                        (DpUtils.getWidthPixels() * designSize.toFloat() / designWidth).toInt()
                    designHeight != INVALID_VALUE -> //按照高度进行计算
                        (DpUtils.getHeightPixels() * designSize.toFloat() / designHeight).toInt()
                    else -> getTransformValue(designSize, true)
                }
            }
        }

        /**
         * DES: 设置目标视图的leftMargin方法
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:03
         *refresh immediately
         * @param targetView 目标视图
         * @param leftMargin 右边边距值
         * @param isDpValue 是否dp单位值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setLeftMargin(targetView: View?,
                          leftMargin: Int,
                          isDpValue: Boolean = true,
                          isImmediateRefresh: Boolean = true) {
            setMargin(
                    targetView,
                    leftMargin,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    isDpValue = isDpValue,
                    isImmediateRefresh = isImmediateRefresh)
        }

        /**
         * DES: 设置目标视图的topMargin方法
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:03
         *refresh immediately
         * @param targetView 目标视图
         * @param topMargin 顶部边距值
         * @param isDpValue 是否dp单位值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setTopMargin(targetView: View?,
                         topMargin: Int,
                         isDpValue: Boolean = true,
                         isImmediateRefresh: Boolean = true) {
            setMargin(
                    targetView,
                    INVALID_VALUE,
                    topMargin,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    isDpValue = isDpValue,
                    isImmediateRefresh = isImmediateRefresh)
        }


        /**
         * DES: 设置目标视图的rightMargin方法
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:03
         *refresh immediately
         * @param targetView 目标视图
         * @param rightMargin 右边边距值
         * @param isDpValue 是否dp单位值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setRightMargin(targetView: View?,
                           rightMargin: Int,
                           isDpValue: Boolean = true,
                           isImmediateRefresh: Boolean = true) {
            setMargin(
                    targetView,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    rightMargin,
                    INVALID_VALUE,
                    isDpValue = isDpValue,
                    isImmediateRefresh = isImmediateRefresh)
        }

        /**
         * DES: 设置目标视图的bottomMargin方法
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:03
         *refresh immediately
         * @param targetView 目标视图
         * @param bottomMargin 底部边距值
         * @param isDpValue 是否dp单位值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setBottomMargin(targetView: View?,
                            bottomMargin: Int,
                            isDpValue: Boolean = true,
                            isImmediateRefresh: Boolean = true) {
            setMargin(
                    targetView,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    INVALID_VALUE,
                    bottomMargin = bottomMargin,
                    isDpValue = isDpValue,
                    isImmediateRefresh = isImmediateRefresh)
        }

        /**
         * DES: 设置目标视图的margin方法
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:03
         *refresh immediately
         * @param targetView 目标视图
         * @param leftMargin 右边边距值,单位是px,不需要设置可传INVALID_VALUE
         * @param topMargin 顶部边距值,单位是px,不需要设置可传INVALID_VALUE
         * @param rightMargin 右边边距值,单位是px,不需要设置可传INVALID_VALUE
         * @param bottomMargin 底部边距值,单位是px,不需要设置可传INVALID_VALUE
         * @param isDpValue 是否dp单位值
         * @param isImmediateRefresh 是否立即刷新
         **/
        @JvmStatic
        @JvmOverloads
        fun setMargin(targetView: View?,
                      leftMargin: Int,
                      topMargin: Int,
                      rightMargin: Int,
                      bottomMargin: Int,
                      isDpValue: Boolean = true,
                      isImmediateRefresh: Boolean = true) {
            //防止意外发生，强转失败导致的异常
            if (targetView?.layoutParams !is ViewGroup.MarginLayoutParams) {
                return
            }
            //获取目标视图布局参数，如果为空则直接返回
            val layoutParam = targetView.layoutParams as ViewGroup.MarginLayoutParams? ?: return
            //用来判断值是否发生改变，有改变则更新
            //否则不更新，防止多次刷新，造成的性能问题
            var isChangeValue = false
            //保存新值元组对，包含单个值是否改变以及新值
            var newValuePair: Pair<Boolean, Int>
            //如果leftMargin不等于无效值则进行计算
            if (leftMargin != INVALID_VALUE) {
                //获取left边距的新计算的值
                newValuePair = getPairValue(layoutParam.leftMargin, leftMargin, isDpValue)
                //判断值是否发生改变
                if (newValuePair.first) {
                    isChangeValue = true
                    //如果有发生变化则替换
                    layoutParam.leftMargin = newValuePair.second
                }
            }
            //如果topMargin不等于无效值则进行计算
            if (topMargin != INVALID_VALUE) {
                //获取top边距的新计算的值
                newValuePair = getPairValue(layoutParam.topMargin, topMargin, isDpValue)
                //判断值是否发生改变
                if (newValuePair.first) {
                    isChangeValue = true
                    //如果有发生变化则替换
                    layoutParam.topMargin = newValuePair.second
                }
            }
            //如果rightMargin不等于无效值则进行计算
            if (rightMargin != INVALID_VALUE) {
                //获取top边距的新计算的值
                newValuePair = getPairValue(layoutParam.rightMargin, rightMargin, isDpValue)
                //判断值是否发生改变
                if (newValuePair.first) {
                    isChangeValue = true
                    //如果有发生变化则替换
                    layoutParam.rightMargin = newValuePair.second
                }
            }
            //如果bottomMargin不等于无效值则进行计算
            if (bottomMargin != INVALID_VALUE) {
                //获取top边距的新计算的值
                newValuePair = getPairValue(layoutParam.bottomMargin, bottomMargin, isDpValue)
                //判断值是否发生改变
                if (newValuePair.first) {
                    isChangeValue = true
                    //如果有发生变化则替换
                    layoutParam.bottomMargin = newValuePair.second
                }
            }
            //判断是否需要更新
            if (isImmediateRefresh && isChangeValue) {
                //如果有任何边距值改变则更新
                targetView.layoutParams = layoutParam
            }
        }

        /**
         * DES: 设置目标视图的右padding
         * AUTHOR: Robot
         * TIME: 2019/9/27 9:55
         * @param targetView 目标视图
         * @param right 右padding单位是dp
         **/
        @JvmStatic
        fun setPaddingRight(targetView: View?, right: Int) {
            setPadding(targetView, INVALID_VALUE, right = right)
        }

        /**
         * DES: 设置目标视图的top padding
         * AUTHOR: Robot
         * TIME: 2019/9/27 9:55
         * @param targetView 目标视图
         * @param top top, 单位是dp， 如果不想设置传
         **/
        @JvmStatic
        fun setPaddingTop(targetView: View?, top: Int) {
            setPadding(targetView, INVALID_VALUE, top = top)
        }


        /**
         * DES: 设置目标视图的bottom padding
         * AUTHOR: Robot
         * TIME: 2019/9/27 9:55
         * @param targetView 目标视图
         * @param bottom bottom, 单位是dp， 如果不想设置传
         **/
        @JvmStatic
        fun setPaddingBottom(targetView: View?, bottom: Int) {
            setPadding(targetView, INVALID_VALUE, bottom = bottom)
        }

        /**
         * DES: 设置目标视图的padding
         * AUTHOR: Robot
         * TIME: 2019/9/27 9:48
         *
         * @param targetView 目标视图
         * @param left 左padding单位是dp， 如果不想设置传，INVALID_VALUE
         * @param top 顶padding单位是dp， 如果不想设置传，INVALID_VALUE
         * @param right 右padding单位是dp， 如果不想设置传，INVALID_VALUE
         * @param bottom 底padding单位是dp， 如果不想设置传，INVALID_VALUE
         **/
        @JvmOverloads
        @JvmStatic
        fun setPadding(targetView: View?,
                       left: Int,
                       top: Int = INVALID_VALUE,
                       right: Int = INVALID_VALUE,
                       bottom: Int = INVALID_VALUE) {
            targetView ?: return
            targetView.setPadding(
                    getPadding(left, targetView.paddingLeft),
                    getPadding(top, targetView.paddingTop),
                    getPadding(right, targetView.paddingRight),
                    getPadding(bottom, targetView.paddingBottom))
        }

        /**
         * DES: 获取padding值
         * AUTHOR: Robot
         * TIME: 2019/9/27 9:41
         *
         * @param value 需要返回的值， 单位dp
         * @param defaultValue 默认的值， 单位dp
         * @return 返回padding值
         **/
        private fun getPadding(value: Int, defaultValue: Int = 0): Int {
            return if(value != INVALID_VALUE) {
                getAdapterSize(value)
            } else {
                defaultValue
            }
        }

        /**
         * DES: 获取元组数据值
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:35
         *
         * @param oldMargin 老的边距值
         * @param newMargin 新的边距值
         * @param isDpValue 是否需要dp转像素
         **/
        private fun getPairValue(oldMargin: Int,
                                 newMargin: Int,
                                 isDpValue: Boolean): Pair<Boolean, Int> {
            //计算新值
            val transformValue = getTransformValue(newMargin, isDpValue)
            //返回元组数据
            return Pair(transformValue != oldMargin, newMargin)
        }

        /**
         * DES: 获取转换后值，将dp转换
         * AUTHOR: Robot
         * TIME: 2019/4/11 11:28
         *
         * @param margin 边距值
         * @param isDpValue 是否转换成像素值
         * @return
         **/
        private fun getTransformValue(margin: Int, isDpValue: Boolean): Int {
            return if (isDpValue) {
                (margin * CxtUtils.getDensity() + .5f).toInt()
            } else {
                margin
            }
        }

        /**
         * DES: 设置文本颜色值
         * AUTHOR: Robot
         * TIME: 2019/7/9 15:39
         *
         * @param targetView 目标文本视图
         * @param color 文本颜色值
         **/
        @JvmStatic
        fun setTextColor(targetView: TextView?, @ColorRes color: Int) {
            targetView?.setTextColor(CxtUtils.getColor(color))
        }

        /**
         * DES: 批量设置文本颜色值
         * AUTHOR: Robot
         * TIME: 2019/7/9 15:39
         *
         * @param targetViews 目标文本视图
         * @param color 文本颜色值
         **/
        @JvmStatic
        fun setTextColors(@ColorRes color: Int, vararg targetViews: TextView?) {
            targetViews.forEach {
                setTextColor(it, color)
            }
        }
    }
}