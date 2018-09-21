package com.gpayne.cover_and_loading


class ShowCoverType(val imageResource:Int? = null,val title:String? = null){

    companion object {

        fun error(imageResource:Int? = R.drawable.show_cover_no_net,title:String? = "网络请求错误，请稍后重试"): ShowCoverType {
            return ShowCoverType(imageResource, title)
        }

        fun noData(imageResource:Int? = R.drawable.show_cover_no_data,title:String? = "暂无数据"): ShowCoverType {
            return ShowCoverType(imageResource, title)
        }
    }
}