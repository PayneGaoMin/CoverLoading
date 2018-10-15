package com.gpayne.cover_and_loading

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.lang.ref.WeakReference

object ShowCoverUtils{

    private var lastTargetView:WeakReference<View>? = null
    private var lastCoverView:WeakReference<View>? = null

    fun bind(targetView:View,coverView:View,listener: (CoverListener.()->Unit)? = null){
        dismiss()
        val parent = targetView.parent as? ViewGroup ?: return
        val layout = RelativeLayout(parent.context)
        layout.addView(coverView,RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT).also {
            it.addRule(RelativeLayout.CENTER_IN_PARENT)
        })
        lastCoverView = WeakReference(layout)
        listener?.let {
            val coverListener = CoverListener()
            coverListener.it()
            coverListener._onConfig?.invoke(coverView)
            coverView.setOnClickListener { _ ->
                coverListener._onReload?.invoke(targetView)
            }
        }
        lastTargetView = WeakReference(targetView)
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

    fun dismiss(){
        lastCoverView?.get()?.run {
            (this.parent as? ViewGroup)?.removeView(this)
        }
        lastTargetView?.get()?.visibility = View.VISIBLE
    }

    fun bind(imageResource:Int?,title:String?,targetView: View,listener: (CoverListener.()->Unit)? = null){
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
        bind(targetView, view, listener)
    }

    fun bind(type: ShowCoverType, targetView: View, listener: (CoverListener.()->Unit)? = null) {
        bind(type.imageResource, type.title, targetView, listener)
    }

    fun bind(layout:Int,targetView: View,listener: (CoverListener.()->Unit)? = null){
        val coverView = View.inflate(targetView.context,layout,null)
        bind(targetView, coverView, listener)
    }

    fun bindLoading(targetView: View,title: String? = null) {
        dismiss()
        val view = View.inflate(targetView.context, R.layout.gp_cover_layout_loading,null)
        view.findViewById<TextView>(R.id.tv_title).run {
            this.visibility = if (title?.isEmpty() != false) View.GONE else View.VISIBLE
            this.text = title
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val progressBar = view.findViewById<ProgressBar>(R.id.pb_loading)
            progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
        }
        bind(targetView, view)
    }

    fun bindLoading(targetActivity:Activity,title: String? = null) {
        dismiss()
        targetActivity.findViewById<ViewGroup>(android.R.id.content).also {
            val loadingView = View.inflate(targetActivity,R.layout.gp_cover_layout_loading,null)
            lastCoverView = WeakReference(loadingView)
            loadingView.setOnClickListener { _ ->}
            loadingView.findViewById<TextView>(R.id.tv_title).also { textView ->
                textView.text = title
                textView.visibility = if (title?.isEmpty() != false) View.GONE else View.VISIBLE
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val progressBar = loadingView.findViewById<ProgressBar>(R.id.pb_loading)
                progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
            }

            it.addView(loadingView,it.childCount, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER))
        }
    }
}