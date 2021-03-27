package com.jiuwan.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.jiuwan.utils.R
import java.util.concurrent.atomic.AtomicBoolean


class StickyLayout
@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle)
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

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.e(TAG, "onNestedScroll")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Log.e(TAG, "onNestedScroll")
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e(TAG, "onNestedPreScroll")
        if(toolbarHieght<=0) {
            //todo:consume fling but not fling0
            Log.e(
                TAG,
                "toolbar height not set,consume all dy but not Scroll,so child do not scroll either"
            )
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


    var viewPortHeight =0

    val lock =AtomicBoolean(true)

    var originalLayoutParamsHeight=0

    val heightLock =AtomicBoolean(true)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if(measuredHeight>0){
            if(lock.compareAndSet(true,false))
                viewPortHeight=measuredHeight
        }

        val params: ViewGroup.LayoutParams = mViewPager.getLayoutParams()

        if(heightLock.compareAndSet(true,false)) originalLayoutParamsHeight=params.height

        params.height = viewPortHeight - mNav.measuredHeight - toolbarHieght.also {
            Log.e(TAG, "onMeasure toolbar height:${toolbarHieght}", )
        }
        if(toolbarHieght>0){mViewPager.post { mViewPager.requestLayout() }}


        setMeasuredDimension(
            measuredWidth,
            mTop.measuredHeight + viewPortHeight-toolbarHieght
        )
    }


    var  toolbarHieght= 0

    fun applyToolBarInsets(toolbarHeight:Int){
        toolbarHieght=toolbarHeight.also {
            Log.e(TAG, "applyToolBarInsets: ${toolbarHeight}", )
        }
        post{
            requestLayout()
        }
    }


    var mTopViewHeight=0

    lateinit var mViewPager:ViewPager

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

        val view = findViewById<View>(R.id.sticky_layout_viewpager) as? ViewPager
                ?: throw RuntimeException(
                        "id_stickynavlayout_viewpager show used by ViewPager !")
        mViewPager = view
    }




}