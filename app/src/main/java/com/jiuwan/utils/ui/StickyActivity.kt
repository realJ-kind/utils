package com.jiuwan.utils.ui

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.color.MaterialColors
import com.jiuwan.utils.R
import com.jiuwan.utils.databinding.ActivityStickyBinding
import com.liulishuo.okdownload.OkDownloadProvider.context
import java.math.BigDecimal
import java.math.RoundingMode


class StickyActivity:AppCompatActivity() {

    lateinit var binding:ActivityStickyBinding

    var inset=false

    val toolbar
    get() = binding.toolbar
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sticky)
        binding.root.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        val view:View =binding.toolbar.apply { }
        view.doOnApplyWindowInsets { view, windowInsetsCompat, viewPaddingState ->
            view.updatePadding(top = windowInsetsCompat.systemWindowInsetTop + viewPaddingState.top)
            if(windowInsetsCompat.systemWindowInsetTop>=0)
                inset=true
        }
        binding.logo.doOnApplyWindowInsets { view, windowInsetsCompat, viewPaddingState ->
            view.updatePadding(top = windowInsetsCompat.systemWindowInsetTop + viewPaddingState.top)
        }
        view.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if(inset){
                val toolbarheight=v.height
                findViewById<StickyLayout>(R.id.sticky_layout).applyToolBarWithInsets(toolbarheight)
            }
        }
        binding.stickyLayoutStickyHeader.setViewPager(binding.stickyLayoutViewpager, arrayOf("详情", "评论", "精选"), this,
                arrayListOf(StickyFragment(), StickyFragment(), StickyFragment()))

        val maxHeight = dp2px(170).toInt()



        val color: Int = (MaterialColors.getColor(toolbar,R.attr.colorSurface)) and 0x00ffffff

        binding.stickyLayout.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

               if(scrollY>maxHeight) return@setOnScrollChangeListener
               var scrolled=Math.min(maxHeight, scrollY)
               scrolled=Math.max(0, scrolled)
               val percent=scrolled / maxHeight.toFloat()
               toolbar.setBackgroundColor(((255f * percent).toInt() shl 24) or color)
            binding.tvTitle.alpha=percent


        }
    }
}

fun Float.scale2()= this.run {
    val decimal = BigDecimal(this.toString()).setScale(2, RoundingMode.HALF_UP)
    decimal.toFloat()
}

fun dp2px(dpValue: Int): Float {
    return (0.5f + dpValue * density)
}

//</editor-fold>
private val density: Float = Resources.getSystem().displayMetrics.density
