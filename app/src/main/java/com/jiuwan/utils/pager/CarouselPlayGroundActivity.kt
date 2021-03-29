package com.jiuwan.utils.pager

import android.content.res.Resources
import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.jiuwan.utils.R
import com.jiuwan.utils.databinding.ActivityCarouselPlayGroundBinding

class CarouselPlayGroundActivity : AppCompatActivity() {

    lateinit var binding:ActivityCarouselPlayGroundBinding
    val pager get() = binding.pager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_carousel_play_ground)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        pager.adapter = pagerAdapter
        /*pager.setPageTransformer(ZoomOutPageTransformer())*/
        Handler().postDelayed({
            pager.setCurrentItem(Int.MAX_VALUE / 2, false)
        }, 100)
        pager.apply {
          /*  clipToPadding=false
            //
            sdetPadding((density * 40).toInt(), 0, (density * 40).toInt(), 0)*/
            //pager.setPageTransformer(MarginPageTransformer(density*40));

            val offsetPx= density * 30
            val pageMarginPx= density * 20
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            setPageTransformer { page, position ->
                val viewPager = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx + pageMarginPx)
                if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }

        }

    }





    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private val NUM_PAGES: Int=Integer.MAX_VALUE

        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment()
    }
}
val density by lazy {
    Resources.getSystem().displayMetrics.density.toInt()
}

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f


class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    val mMarginPx = density*80

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]



                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))

                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                   /* translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }*/

                    // Scale the page down (between MIN_SCALE and 1)
                  /*  scaleX = scaleFactor
                    scaleY = scaleFactor*/

                    // Fade the page relative to its size.
                    alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))

                    val base=0.5f
                    scaleX= ( base + (1-Math.abs(position)) *0.5f )
                    scaleY= ( base + (1-Math.abs(position)) *0.5f )

                    rotationY= - position * 30



                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }

           /* val offset: Float = mMarginPx .toFloat()
                view.setTranslationX(-offset)*/

        }
    }
}






