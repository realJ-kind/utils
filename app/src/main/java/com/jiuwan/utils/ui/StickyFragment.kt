package com.jiuwan.utils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyDataBindingPattern
import com.airbnb.epoxy.EpoxyRecyclerView
import com.jiuwan.utils.R
import com.jiuwan.utils.simpleText


class StickyFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EpoxyRecyclerView>(R.id.rv).apply {
            withModels {
                repeat(100){
                    //一个简单的textview
                    simpleText{
                        id("$it")
                        adapterPositon(it)
                    }
                }

            }
        }
    }
}
