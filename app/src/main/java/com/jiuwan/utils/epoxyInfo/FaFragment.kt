package com.jiuwan.utils.epoxyInfo

import com.jiuwan.utils.R

class FaFragment:CenterDialogFragment() {
    companion object{
        val TAG= FaFragment::class.java.canonicalName
    }
    override fun layoutId(): Int {
        return R.layout.dialog_center_template
    }

    override fun initWhenViewCreated() {
    }
}