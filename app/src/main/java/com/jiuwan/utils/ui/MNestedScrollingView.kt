package com.jiuwan.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class MNestedScrollingView @JvmOverloads constructor(context: Context,
                                                         attributeSet: AttributeSet?=null,
                                                         defStyleAttr:Int=0
) :NestedScrollView(context,attributeSet,defStyleAttr){

    var downX=0f
    var downY=0f

    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        val x = e.rawX
        val y = e.rawY
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                //将按下时的坐标存储
                downX = x
                downY = y
                // true 表示让ParentRecyclerView不要拦截
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                //获取到距离差
                val dx: Float = x - downX
                val dy: Float = y - downY
                // 通过距离差判断方向
                val orientation = getOrientation(dx, dy)
                val location = intArrayOf(0, 0)
                getLocationOnScreen(location)
                when (orientation) {
                    "d" -> if (canScrollVertically(-1)) {
                        // 可以向下滑动时让ParentRecyclerView不要拦截
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else { //内层RecyclerView下拉到最顶部时
                        if(dy < 24f){
                            // 如果滑动的距离小于这个值依然让Parent不拦截
                            parent.requestDisallowInterceptTouchEvent(true)
                        }else{
                            // 将滑动事件抛给Parent，这样可以随着Parent一起滑动
                            parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    "u" -> {
                        // 向上滑动时，始终由ChildRecyclerView处理
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(e)
    }

    private fun getOrientation(dx: Float, dy: Float): String {
        return if (Math.abs(dx) > Math.abs(dy)) {
            //X轴移动
            if (dx > 0) "r" else "l" //右,左
        } else {
            //Y轴移动
            if (dy > 0) "d" else "u" //下//上
        }
    }

}