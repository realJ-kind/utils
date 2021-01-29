package com.jiuwan.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView

@BindingAdapter("hobbitsData","hobbitHandler")
fun epoxyBindHobbits(epoxyRecyclerVeiew: EpoxyRecyclerView,hobbits: Hobbits?,hobbitHandler:HobbitHanler){
    epoxyRecyclerVeiew.layoutManager=LinearLayoutManager(epoxyRecyclerVeiew.context,LinearLayoutManager.VERTICAL,false)
    epoxyRecyclerVeiew.withModels {

        hobbits?.data?.forEach { item->
            perosnHobbits {
                id("list_hobbits")
                hobbit(item)
                click(View.OnClickListener {
                    hobbitHandler.handleHobbitClick(item)
                })
            }

        }
    }
}

interface HobbitHanler{
    fun handleHobbitClick(hobbit:String)
}
