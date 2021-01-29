package com.jiuwan.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyRecyclerView
import com.jiuwan.platformapp.utils.showToast
import com.jiuwan.utils.databinding.ActivityMainBinding


//test epoxy
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner=this
        binding.epoxyRv.withModels {
            person {
                id("p2")
                dataClass1(DataClass1("hahah","chilili"))
                hobbits(Hobbits(mutableListOf<String>().apply {
                    add("121sdf213");add("12121325safsadf3");add("121234513");add("12sfasdf34561213");add("121sadfds213");add("121213");
                    add("3245");add("1212ass13");add("fsad12121asdf3");add("12kj;1213");add("12kj;1213");add("12kj;1213");
                    add("12hk1213");add("121ghkj213");add("121jh;213");add("12k;1213");add("121kl213");add("121kj;213");
                    add("12sdfs1213");add("1212hjk13");add("121213");add("121213");add("1sadfds21213");add("121213");
                    add("1212123453253");add("121hjk213");add("121213");add("121213");add("121213");add("121213");
                    add("12ghj1213");add("121fgh213");add("1asdf21213");add("121dsfda213");add("121213");add("121213");
                    add("121213");add("121213");add("1q345321213");add("121213");add("121213");add("121213");
                    add("121213");add("121234523213");add("12werw1213");add("121213");add("121213");add("121213");
                }))
                hobbitHandler(object :HobbitHanler{
                    override fun handleHobbitClick(hobbit: String) {
                        showToast("you clicked ${hobbit}")
                    }
                })
            }
        }
    }
}