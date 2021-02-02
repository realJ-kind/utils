package com.jiuwan.utils

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jiuwan.utils.databinding.ActivityCarrouselBinding
import com.jiuwan.utils.epoxyInfo.CirclePagerIndicatorDecoration
import com.jiuwan.utils.epoxyInfo.CirclePageIndicatorCarousel
import com.jiuwan.utils.epoxyInfo.HaFragment
import com.jiuwan.utils.epoxyInfo.circlePageIndicatorCarousel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.lang.Exception

class CarouselActivity:AppCompatActivity() {

    lateinit var binding: ActivityCarrouselBinding

    var attached=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this, R.layout.activity_carrousel)
         binding.bt.setOnClickListener {
             refreshData(getNewList())
         }
    }

    var count=1

    val list= mutableListOf<String>().apply {
        repeat(count){
            add("帅帅")
        }
    }

    private fun getNewList(): List<String> {
        /*count++
        val data= mutableListOf<String>()
        repeat(count){
            data.add("帅帅")
        }
        return data*/
        list.add("帅帅").also { return list }
    }

    val circlePagerIndicatorDecoration by lazy {CirclePagerIndicatorDecoration()}

    var decorated=false

    var wraperred=false


    private fun refreshData(data:List<String>) {
        val rv=binding.rv
        val models= mutableListOf<TestcarBindingModel_>().apply {
            list.forEach { displayData ->
                add(TestcarBindingModel_()
                    .id("lunbo item")
                    .display(displayData)
                    .click(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            try { HaFragment().apply {
                                    arguments=Bundle()
                                }.show(supportFragmentManager,HaFragment.TAG)
                            }
                            catch (e:Exception){

                            }
                        }

                    })
                )
            }
        }

        rv.withModels {
            circlePageIndicatorCarousel {
                id("carousel")
                numViewsToShowOnScreen(1.2f)
                        .models(models)
                paddingDp(20)
                onBind { model, view: CirclePageIndicatorCarousel, position ->
                    if(true){
                        wraperred=true
                        view.adapter= (view.adapter?.let { AlphaInAnimationAdapter(it) })?.let { ScaleInAnimationAdapter(it) }
                    }
                }
            }
            repeat(100){
                justColor {
                    id("just_color")
                }
            }
        }

    }
}
