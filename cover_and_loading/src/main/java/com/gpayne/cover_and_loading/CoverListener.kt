package com.gpayne.cover_and_loading

import android.view.View

class CoverListener{
    var _onConfig:((target:View)->Unit)? = null
    fun onConfig(listener:((view:View)->Unit)?){
        _onConfig = listener
    }

    var _onReload:((view:View)->Unit)? = null

    fun onReload(listener: ((view: View) -> Unit)?){
        _onReload = listener
    }


}