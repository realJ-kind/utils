package com.jiuwan.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import com.jiuwan.utils.R


class StickyLayout
@JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle)
        , NestedScrollingParent3{

    init {
        orientation= VERTICAL
    }

    private val TAG = "StickyNavLayout"


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.e(TAG, "onNestedScrollAccepted")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        Log.e(TAG, "onStopNestedScroll")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedScroll")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        Log.e(TAG, "onNestedScroll")
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e(TAG, "onNestedPreScroll")
        if(toolbarHieght<=0) {
            //todo:consume fling but not fling0
            Log.e(TAG, "toolbar height not set,consume all dy but not Scroll,so child do not scroll either")
            consumed[1]=dy
            return
        }
        //scroll if not fully collasped
        val hiddenTop = dy > 0 && scrollY < (mTopViewHeight-toolbarHieght) //conten-toolbar
        val showTop = dy < 0 && scrollY > 0 && !ViewCompat.canScrollVertically(target, -1)
        if (hiddenTop || showTop) {
            if( (scrollY+dy <= (mTopViewHeight-toolbarHieght))  && scrollY+dy>=0 ){
                scrollBy(0, dy)
                consumed[1] = dy
            }else if(dy>0){
                val part= mTopViewHeight-toolbarHieght-scrollY
                scrollBy(0, part)
                consumed[1] = part
            }else{
                val part= 0-scrollY
                scrollBy(0, part)
                consumed[1] = part
            }
        }
        val dis=if (scrollY>mTopViewHeight-toolbarHieght) mTopViewHeight-toolbarHieght  else if(scrollY<0) 0 else scrollY
        val percent=dis.toFloat() /(mTopViewHeight-toolbarHieght)
        mTop.alpha=percent

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    var  toolbarHieght= -1


    var mTopViewHeight=0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = mTop.getMeasuredHeight()
    }

    lateinit var mTop: View

    lateinit var mNav: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        mTop = findViewById<View>(R.id.sticky_layout_content)
        mNav = findViewById<View>(R.id.sticky_layout_sticky_header)
     /*   val view = findViewById<View>(R.id.id_stickynavlayout_viewpager) as? ViewPager
                ?: throw RuntimeException(
                        "id_stickynavlayout_viewpager show used by ViewPager !")
        mViewPager = view*/
    }



}