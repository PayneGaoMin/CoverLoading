package com.gpayne.cover_and_loading

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

object ShowCoverUtils{

    private const val coverTag = "gp_show_cover_utils_tag"

    fun bind(targetView:View,coverView:View,onReload:((coverView:View)->Unit)? = null){
        dismiss(targetView)
        val parent = targetView.parent as? ViewGroup ?: return
        val layout = RelativeLayout(parent.context)
        layout.addView(coverView,RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT).also {
            it.addRule(RelativeLayout.CENTER_IN_PARENT)
        })
        layout.tag = coverTag
        onReload?.run {
            coverView.setOnClickListener {
                this.invoke(targetView)
            }
        }
        val index = parent.indexOfChild(targetView)
        parent.addView(layout,index + 1,targetView.layoutParams)
        when (parent) {
            is LinearLayout -> {
                targetView.visibility = View.GONE
            }
            else -> {
                targetView.visibility = View.INVISIBLE
            }
        }
    }

    fun dismiss(targetActivity: Activity){
        targetActivity.findViewById<ViewGroup>(android.R.id.content).also {
            it.findViewWithTag<View>(coverTag)?.run {
                it.removeView(this)
            }
        }
    }

    fun dismiss(targetView: View){
        val parent = targetView.parent as? ViewGroup ?: return
        parent.findViewWithTag<View>(coverTag)?.run {
            parent.removeView(this)
        }
        targetView.visibility = View.VISIBLE
    }

    fun bind(imageResource:Int?,title:String?,targetView: View,onReload:((coverView:View)->Unit)? = null){
        val view = View.inflate(targetView.context, R.layout.gp_cover_layout,null)
        view.findViewById<TextView>(R.id.tv_title).run {
            this.visibility = if (title?.isEmpty() != false) View.GONE else View.VISIBLE
            this.text = title
        }

        view.findViewById<ImageView>(R.id.iv_image).run {
            this.visibility = if ((imageResource ?: 0) != 0) View.VISIBLE else View.GONE
            imageResource?.let {
                this.setImageResource(it)
            }
        }
        bind(targetView, view, onReload)
    }

    fun bind(type: ShowCoverType, targetView: View, onReload: ((coverView: View) -> Unit)? = null) {
        bind(type.imageResource, type.title, targetView, onReload)
    }

    fun bind(layout:Int,targetView: View,onReload: ((coverView: View) -> Unit)? = null){
        val coverView = View.inflate(targetView.context,layout,null)
        bind(targetView, coverView, onReload)
    }

    fun bindLoading(targetView: View,title: String? = null) {
        val resource = targetView.context.resources
        val id = resource.getIdentifier("gp_cover_layout_loading","layout",targetView.context.packageName)
        val view = View.inflate(targetView.context, id,null)
        val textId = resource.getIdentifier("tv_title","id",targetView.context.packageName)
        view.findViewById<TextView>(textId).run {
            this.visibility = if (title?.isEmpty() != false) View.GONE else View.VISIBLE
            this.text = title
        }
        bind(targetView, view)
    }

    fun bindLoading(targetActivity:Activity,title: String? = null) {
        dismiss(targetActivity)
        val resource = targetActivity.resources
        targetActivity.findViewById<ViewGroup>(android.R.id.content).also {
            val id = resource.getIdentifier("gp_cover_layout_loading","layout",targetActivity.packageName)
            val loadingView = View.inflate(targetActivity,id,null)
            loadingView.tag = coverTag
            loadingView.setOnClickListener { _ ->}
            loadingView.findViewById<TextView>(R.id.tv_title).also { textView ->
                textView.text = title
                textView.visibility = if (title?.isEmpty() != false) View.GONE else View.VISIBLE
            }
            it.addView(loadingView,it.childCount, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER))
        }
    }
}