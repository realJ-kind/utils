package com.jiuwan.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.airbnb.epoxy.EpoxyRecyclerView

class CustomList @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : EpoxyRecyclerView(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.action==MotionEvent.ACTION_DOWN){
            requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(ev)
    }
}