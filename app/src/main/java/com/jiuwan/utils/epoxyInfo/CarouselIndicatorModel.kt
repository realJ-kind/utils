package com.jiuwan.utils.epoxyInfo

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.ModelView

class CarouselIndicatorModel : CarouselModel_() {

    val indicator = CirclePagerIndicatorDecoration()

    override fun bind(carousel: Carousel) {
        super.bind(carousel)

        carousel.addItemDecoration(indicator)
    }

    override fun unbind(carousel: Carousel) {
        super.unbind(carousel)

        carousel.removeItemDecoration(indicator)
    }
}