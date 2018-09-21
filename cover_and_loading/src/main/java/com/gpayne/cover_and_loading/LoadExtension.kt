package com.gpayne.cover_and_loading

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View

public fun <T:View> T.showLoading(title:String? = null) {
    ShowCoverUtils.bindLoading(this, title)
}

public fun <T:View> T.showCover(layoutId:Int,callback:((view:View)->Unit)? = null) {
    ShowCoverUtils.bind(layoutId, this, callback)
}

public fun <T:View> T.showCover(view: View,callback:((view:View)->Unit)? = null){
    ShowCoverUtils.bind(this, view, callback)
}

public fun <T:View> T.showCover(type: ShowCoverType, callback:((view:View)->Unit)? = null){
    ShowCoverUtils.bind(type, this, callback)
}

public fun View.dismissCover(){
    ShowCoverUtils.dismiss(this)
}

public fun <T:Activity> T.showLoading(title: String? = null) {
    ShowCoverUtils.bindLoading(this, title)
}

public fun <T: Fragment> T.showLoading(title: String? = null) {
    this.activity?.let {
        ShowCoverUtils.bindLoading(it, title)
    }
}

public fun Activity.dismissLoading(){
    ShowCoverUtils.dismiss(this)
}

public fun Fragment.dismissLoading(){
    this.activity?.let {
        ShowCoverUtils.dismiss(it)
    }
}