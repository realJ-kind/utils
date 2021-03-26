package com.jiuwan.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiuwan.utils.databinding.ActivityFuckBinding
import java.security.AccessController.getContext


class FuckActivity :AppCompatActivity(){

    lateinit var binding: ActivityFuckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_fuck)
        val manger=GridLayoutManager(this,3)
        binding.rv.layoutManager=manger
        binding.rv.withModels {
            spanCount=3
            manger.spanSizeLookup=spanSizeLookup
            repeat(100){
                gridItem {
                    onBind { model, view, position ->

                    }
                    id("griditem")
                    spanSizeOverride { totalSpanCount, position, itemCount ->
                        totalSpanCount/3
                    }
                }
            }

        }
    }
}