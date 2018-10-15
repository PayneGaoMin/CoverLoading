package com.gpayne.cover_and_loading

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View

public fun <T:View> T.showLoading(title:String? = null) {
    ShowCoverUtils.bindLoading(this, title)
}

public fun <T:View> T.showCover(layoutId:Int,listener: (CoverListener.()->Unit)? = null) {
    ShowCoverUtils.bind(layoutId, this, listener)
}

public fun <T:View> T.showCover(view: View,listener: (CoverListener.()->Unit)? = null){
    ShowCoverUtils.bind(this, view, listener)
}

public fun <T:View> T.showCover(type: ShowCoverType, listener: (CoverListener.()->Unit)? = null){
    ShowCoverUtils.bind(type, this, listener)
}

public fun View.dismissCover(){
    ShowCoverUtils.dismiss()
}

public fun <T:Activity> T.showLoading(title: String? = null) {
    ShowCoverUtils.bindLoading(this, title)
}

public fun <T: Fragment> T.showLoading(title: String? = null) {
    this.activity?.let {
        ShowCoverUtils.bindLoading(it, title)
    }
}

public fun Context.dismissLoading(){
    ShowCoverUtils.dismiss()
}
