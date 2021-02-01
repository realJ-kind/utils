package com.jiuwan.utils.epoxyInfo

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
internal class CirclePageIndicatorCarousel(context: Context?) : Carousel(context) {
    var circlePagerIndicatorDecoration: CirclePagerIndicatorDecoration? = null

    override fun init() {
        super.init()
        addItemDecoration(circlePagerIndicatorDecoration?:CirclePagerIndicatorDecoration().also { circlePagerIndicatorDecoration=it })
    }

}