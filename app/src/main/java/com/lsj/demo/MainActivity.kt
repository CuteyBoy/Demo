package com.lsj.demo

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.widget.TextView
import android.widget.Toast
import com.lsj.demo.adapter.CommonAdapter
import com.lsj.demo.adapter.CommonViewHolder
import com.lsj.demo.customview.CustomViewGroupActivity
import com.lsj.demo.customview.ViewLocationInfoActivity
import com.lsj.demo.encrypt.EncryptTestActivity
import com.lsj.demo.mark.MarkTestActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 演示demo主界面
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        demoFuncRv.layoutManager = LinearLayoutManager(this)
        val defaultDividerItem = DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL)
        demoFuncRv.addItemDecoration(defaultDividerItem)
        val funcDataAdapter = object : CommonAdapter<JumpBo>() {
            override fun onLoadLayoutId(viewType: Int): Int = R.layout.view_rv_item

            override fun onBindDataForView(holder: CommonViewHolder, currentData: JumpBo, position: Int) {
                val funcNameView = holder.findViewById<TextView>(R.id.name_tv)
                funcNameView?.text = currentData.name ?: ""
                holder.itemView.setOnClickListener {
                    if(currentData.clz != null) {
                        val intentFunc = Intent(this@MainActivity, currentData.clz)
                        intentFunc.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intentFunc)
                    } else {
                        Toast.makeText(this@MainActivity, "未设置界面class实例", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        demoFuncRv.adapter = funcDataAdapter
        funcDataAdapter.setDataSet(getFuncDataSet())
    }

    /**
     * 获取功能数据集合
     * @return 返回数据集合
     */
    private fun getFuncDataSet(): List<JumpBo>? {
        return arrayListOf(
            JumpBo("View位置参数演示", clz = ViewLocationInfoActivity::class.java),
            JumpBo("商品打标演示", clz = MarkTestActivity::class.java),
            JumpBo("自定义ViewGroup演示", clz = CustomViewGroupActivity::class.java),
            JumpBo("加解密耗时测试", clz = EncryptTestActivity::class.java)
        )
    }
}

// 跳转实例
data class JumpBo(
    // 显示功能名
    var name: String? = null,
    // 描述信息
    var desInfo: String? = null,
    // 目标class
    var clz: Class<*>? = null
)
