package com.jiuwan.utils.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import com.jiuwan.utils.R
import com.jiuwan.utils.databinding.ActivityStickyBinding

class StickyActivity:AppCompatActivity() {

    lateinit var binding:ActivityStickyBinding

    var inset=false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sticky)
        binding.root.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        val view:View =binding.toolbar.apply { }
        view.doOnApplyWindowInsets { view, windowInsetsCompat, viewPaddingState ->
            view.updatePadding(top = windowInsetsCompat.systemWindowInsetTop+viewPaddingState.top)
            if(windowInsetsCompat.systemWindowInsetTop>=0)
                inset=true
        }
        view.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if(inset){
                val toolbarheight=v.height
                findViewById<StickyLayout>(R.id.sticky_layout).applyToolBarInsets(toolbarheight)
            }
        }
        binding.stickyLayoutStickyHeader.setViewPager(binding.stickyLayoutViewpager, arrayOf("111","222","333"),this,
            arrayListOf(StickyFragment(), StickyFragment(),StickyFragment()))
        binding.stickyLayout.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val scrolled=scrollY
            val alpha =if(scrolled<=0) 0f  else if(scrolled>=300) 1f else scrolled.toFloat()/ 600f
            binding.toolbar.alpha=alpha
        }
    }
}