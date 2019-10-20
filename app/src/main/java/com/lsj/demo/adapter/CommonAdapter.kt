package com.lsj.demo.adapter

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @Description: 常用RecyclerView数据适配器
 * @CreateDate: 2019-10-13
 * @Version: 1.0.0
 */
abstract class CommonAdapter<T>(dataSet: ArrayList<T>? = null):
    RecyclerView.Adapter<CommonViewHolder>(){

    // 数据集合
    private var mData = dataSet

    /**
     * 根据视图类型价值返回布局Id
     * @param viewType 视图类型
     * @return item的布局Id
     */
    abstract fun onLoadLayoutId(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val layoutId = onLoadLayoutId(viewType)
        val childView = LayoutInflater.from(parent.context).inflate(layoutId,
            parent, false)
        var viewHolder = onLoadViewHolder(viewType)
        if(viewHolder == null) {
            viewHolder = CommonViewHolder(childView)
        }
        return viewHolder
    }

    fun getItem(position: Int): T? {
        return if(position in 0 until itemCount) {
            mData?.get(position)
        } else {
            null
        }
    }

    /**
     * 设置数据集合
     * @param dataSet 数据集合
     * @param isRefresh 是否立即刷新，默认是true
     */
    fun setDataSet(dataSet: List<T>?, isRefresh: Boolean = true) {
        if(mData == null) {
            mData = ArrayList(10)
        }
        if(dataSet?.isNotEmpty() == true) {
            if(mData?.isNotEmpty() == true) {
                mData?.clear()
            }
            mData?.addAll(dataSet)
            if(isRefresh) {
                notifyDataSetChanged()
            }
        }
    }

    /**
     * 根据ViewType导入视图ViewHolder
     * 默认返回null
     * @param viewType 视图类型
     * @return ViewHolder实例，默认返回null
     */
    fun onLoadViewHolder(viewType: Int): CommonViewHolder? = null

    override fun getItemCount(): Int = mData?.size ?: 0

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val currentData = mData?.get(position)
        if(currentData != null) {
            onBindDataForView(holder, currentData, position)
        }
    }

    /**
     * 为视图绑定数据
     * @param holder 视图支持器
     * @param currentData 当前位置数据
     * @param position 位置
     */
    abstract fun onBindDataForView(holder: CommonViewHolder, currentData: T, position: Int)
}

/**
 * 抽出共同的视图支持器
 * @param itemView item视图Id
 */
class CommonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    /**
     * 根据对应Id找对应的视图实例
     * @param id 视图资源Id
     * @return 目标视图实例，可能返回null
     */
    fun <T: View> findViewById(id: Int): T? {
        return itemView.findViewById(id)
    }

    /**
     * 获取字符资源Id
     * @param id 字符资源Id
     * @param args 参数
     * @return 返回字符串
     */
    fun getString(id: Int, vararg args: Any): String? {
        return itemView.context.getString(id, args)
    }

    /**
     * 获取颜色值
     * @param colorId 颜色资源id
     * @return 颜色值
     */
    fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(itemView.context, colorId)
    }

    /**
     * 根据资源Id获取图标Drawable
     * @param iconResId 资源Id
     * @return Drawable实例
     */
    fun getDrawable(iconResId: Int): Drawable? {
        return ContextCompat.getDrawable(itemView.context, iconResId)
    }
}
