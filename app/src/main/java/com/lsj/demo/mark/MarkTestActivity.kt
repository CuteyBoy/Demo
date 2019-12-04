package com.lsj.demo.mark

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.lsj.demo.R
import com.lsj.demo.adapter.CommonAdapter
import com.lsj.demo.adapter.CommonViewHolder
import com.lsj.demo.customview.OverflowView
import com.lsj.demo.drawables.ShapeBuilder
import com.lsj.demo.utils.DpUtils
import com.lsj.demo.utils.ViewModifyUtils
import kotlinx.android.synthetic.main.activity_mark_view.*
import kotlin.random.Random

/**
 * @CreateDate: 2019-10-19
 * @Version: 1.0.0
 */
class MarkTestActivity: FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_view)
        markListRv.layoutManager = LinearLayoutManager(this)
        val defaultDividerItem = DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL)
        markListRv.addItemDecoration(defaultDividerItem)
        markListRv.adapter = mMarkAdapter

        setTestData()
    }

    private fun setTestData() {
        val markNameList = arrayListOf(
            "特卖",
            "自营",
            "云集超市满99",
            "云集国际云云满99云集国满99集国超",
            "特卖.满99减10"
        )
        val atmosphereUrl = "http://image.yunjiweidian.com/admin_stresstest/ae2c122d929f165b2142a75c752d94f6.png"
        val itemData = ArrayList<ItemBo>()
        for (index in 0..80) {
            var tempAtmosphereUrl: String? = null
            val markList = ArrayList<MarkBo>()
            for (j in 0..Random.nextInt(0, 8)) {
                if(j == 0) {
                    tempAtmosphereUrl = getRandomMarkUrl(atmosphereUrl)
                }
                val markName = if(tempAtmosphereUrl?.isNotEmpty() == true) {
                    null
                } else {
                    getRandomMark(markList, markNameList)
                }
                markList.add(MarkBo(tempAtmosphereUrl, markName))
                tempAtmosphereUrl = null
            }
            val itemBo = ItemBo("名字$index", markList)
            itemBo.overflow = "月销1000".plus(index).plus("件")
            itemBo.content = itemBo.overflow.plus("---").plus(index).plus("---").plus(getRandomMark(markList, markNameList))
            itemData.add(itemBo)
        }
        mMarkAdapter.setDataSet(itemData)
    }

    private fun getRandomMarkUrl(atmosphere: String): String? {
        return if(Random.nextBoolean()) {
            atmosphere
        } else {
            null
        }
    }

    private fun getRandomMark(markList: List<MarkBo>, markNameList: List<String>): String {
        val random = Random.nextInt(0, markNameList.size)
        val markName = markNameList[random]
        var isEqual = false
        markList.forEach {
            isEqual = it.name == markName
        }
        return if(isEqual) {
            getRandomMark(markList, markNameList)
        } else {
            markName
        }
    }

    private val mMarkAdapter = object : CommonAdapter<ItemBo>() {
        override fun onLoadLayoutId(viewType: Int): Int {
            return R.layout.view_rv_item_mark
        }

        override fun onBindDataForView(holder: CommonViewHolder, currentData: ItemBo, position: Int) {
            val mNameTv = holder.findViewById<TextView>(R.id.name_tv)
            mNameTv?.text = currentData.name ?: ""
            val textText = holder.findViewById<OverflowView>(R.id.overflow_tv)
            textText?.setText(currentData.overflow, currentData.content)
            MarkUtils.setItemMark(holder.findViewById(R.id.mark_container),
                mNameTv, currentData, true)
            holder.itemView.setOnClickListener {
                text_tv.setText(currentData.overflow, currentData.content)
            }
        }
    }
}

data class ItemBo(
    var name: String? = null,
    var markList: List<MarkBo>? = null,
    var overflow: String? = null,
    var content: String? = null
)

data class MarkBo(
    var imgUrl: String? = null,
    var name: String? = null,
    var borderColor: Int = Color.RED
)

object MarkUtils {

    //用于保存唯一的氛围标图标
    private var mAtmosphereDrawable: Drawable? = null

    fun setItemMark(markContainer: LinearLayout?, titleView: TextView?, itemBo: ItemBo, isDouble: Boolean) {
        var markList = itemBo.markList
        var markSize = markList?.size ?: 0
        if(markSize > 4) {
            markList = markList?.subList(0, 4)
            markSize = 4
        }
        var isAtmosphere = false
        var markIndex = -1
        if(markSize > 0) {
            markContainer?.visibility = View.VISIBLE
            markList?.forEach{ markBo ->
                markIndex ++
                when {
                    markBo.imgUrl?.isNotEmpty() == true -> {
                        isAtmosphere = true
                        setAtmosphereMark(markContainer, titleView, markBo, isDouble)
                    }
                    markBo.name?.isNotEmpty() == true -> setNormalMark(markContainer, markIndex, markBo, isDouble, isAtmosphere)
                    else -> {
                        markIndex --
                        markSize --
                    }
                }
            }
        } else {
            markContainer?.visibility = View.GONE
        }
        if(isDouble) {
            val atmosphereView = markContainer?.getChildAt(0)
            if(atmosphereView is ImageView) {
                if(!isAtmosphere) {
                    markIndex ++
                    markSize ++
                    ViewModifyUtils.setVisibility(atmosphereView)
                }
            }
        }
        val markChildCount = markContainer?.childCount ?: 0
        if(markIndex >= (markSize - 1) && markIndex <
            (markChildCount - 1)) {
            val newIndex = if(!isDouble && isAtmosphere) {
                markIndex
            } else {
                markIndex + 1
            }
            for(index in newIndex until markChildCount) {
                ViewModifyUtils.setVisibility(markContainer?.getChildAt(index))
            }
        }
    }

    private fun setAtmosphereMark(markContainer: LinearLayout?, titleView: TextView?, markBo: MarkBo, isDouble: Boolean) {
        markContainer ?: return
        titleView ?: return
        if(isDouble) {
            val cacheView = markContainer.getChildAt(0)
            val atmosphereImage = if(cacheView is ImageView) {
                cacheView
            } else {
                ImageView(markContainer.context)
            }
            if(mAtmosphereDrawable != null) {
                atmosphereImage.setImageDrawable(mAtmosphereDrawable)
            } else if(markBo.imgUrl?.isNotEmpty() == true){
                Glide.with(atmosphereImage.context)
                    .load(markBo.imgUrl)
                    .into(atmosphereImage)
            }
            ViewModifyUtils.setVisibility(atmosphereImage, View.VISIBLE)
            if(cacheView == null || cacheView is TextView) {
                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    DpUtils.dp2px(14f))
                layoutParams.rightMargin = DpUtils.dp2px(4f)
                markContainer.addView(atmosphereImage, 0, layoutParams)
            }
        } else {
            if(mAtmosphereDrawable != null) {
                val titleName = titleView.text?.toString()?.trim() ?: ""
                titleView.text = getMergeIconAndText(titleView.context, mAtmosphereDrawable, titleName)
            } else {
                loadAtmosphereIcon(titleView, markBo.imgUrl)
            }
        }
    }


    private fun setNormalMark(markContainer: LinearLayout?, index: Int, markBo: MarkBo,
                              isDouble: Boolean, isAtmosphere: Boolean) {
        markContainer ?: return
        var newIndex = index
        if(isDouble) {
            val atmosphereView = markContainer.getChildAt(0)
            if(atmosphereView is ImageView) {
                if(!isAtmosphere) {
                    newIndex ++
                }
            }
        } else if(isAtmosphere) {
            newIndex --
        }
        val cacheView = markContainer.getChildAt(newIndex)
        val markView = when (cacheView) {
            is TextView -> cacheView
            else -> TextView(markContainer.context)
        }
        markView.text = markBo.name
        ViewModifyUtils.setVisibility(markView, View.VISIBLE)
        if(cacheView == null) {
            markView.id = View.generateViewId()
            markView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f)
            markView.setTextColor(markBo.borderColor)
            markView.gravity = Gravity.CENTER
            markView.setSingleLine(true)
            markView.paint.isFakeBoldText = false
            ViewModifyUtils.setPadding(markView, left = 2, right = 2)
            ViewCompat.setBackground(markView, ShapeBuilder()
                .corner(3f)
                .strokeInt(0.5f, markBo.borderColor)
                .build())
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DpUtils.dp2px(14f)
            )
            layoutParams.rightMargin = DpUtils.dp2px(4f)
            markContainer.addView(markView, layoutParams)
        }
    }


    private fun loadAtmosphereIcon(titleView: TextView?, markUrl: String?) {
        if(titleView != null && markUrl?.isNotEmpty() == true) {
            Glide.with(titleView.context)
                .load(markUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(object : CustomViewTarget<TextView, Drawable>(titleView) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Log.d("Tag", "onLoadFailed")
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        Log.d("Tag", "onResourceCleared")
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        val titleName = getView().text?.toString()?.trim() ?: ""
                        getView().text = getMergeIconAndText(getView().context, resource, titleName)
                        mAtmosphereDrawable = resource
                    }
                })
        }

    }

    /**
     * DES: 合并图标和文本
     * TIME: 2019/4/8 14:42
     * 此方法只能给氛围图标用，其他地方用了不负责的
     *
     * @param drawable 图标
     * @param text     文本
     * @return android.text.SpannableString 富文本
     */
    private fun getMergeIconAndText(context: Context, drawable: Drawable?, text: String): SpannableString {
        if (drawable == null) {
            return SpannableString(text)
        }
        val h = DpUtils.dp2px(14f)
        var w = DpUtils.dp2px(34f)
        try {
            w = ((h * drawable.intrinsicWidth / drawable.intrinsicHeight).toFloat()).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val newSpan = SpannableString("0000 $text")
        drawable.setBounds(0, 0, w.toInt(), h)
        newSpan.setSpan(object : ImageSpan(drawable) {
            override fun draw(
                canvas: Canvas,
                text: CharSequence,
                start: Int,
                end: Int,
                x: Float,
                top: Int,
                y: Int,
                bottom: Int,
                paint: Paint
            ) {
                val fm = paint.fontMetricsInt
                val drawable = getDrawable().mutate()
                val transY = (y + fm.descent + y + fm.ascent) / 2 - drawable.bounds.bottom / 2
                canvas.save()
                canvas.translate(x, transY.toFloat())
                drawCanvas(drawable, canvas)
                canvas.restore()
            }
        }, 0, "0000".length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return newSpan
    }

    /** DES: 绘制  */
    private fun drawCanvas(drawable: Drawable, canvas: Canvas) {
        var tempDrawable = drawable
        // DES: 判断类型
        if (drawable is TransitionDrawable) {
            tempDrawable = drawable.getDrawable(1)
        }
        // DES: 根据类型绘制
        if (tempDrawable is BitmapDrawable) {
            val bitmapDrawable = drawable as BitmapDrawable
            if (bitmapDrawable.bitmap != null && !bitmapDrawable.bitmap.isRecycled) {
                drawable.draw(canvas)
            }
        } else {
            drawable.draw(canvas)
        }
    }

}