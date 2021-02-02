package com.jiuwan.utils.epoxyInfo

import com.jiuwan.utils.R
import com.jiuwan.utils.RoundBottomSheetDialogFragment

class HaFragment:RoundBottomSheetDialogFragment() {

    companion object{
        val TAG=HaFragment::class.java.canonicalName
    }
    override fun layoutId(): Int {
        return R.layout.dialog_bottom_sheet_template
    }

    override fun initWhenViewCreated() {

    }
}