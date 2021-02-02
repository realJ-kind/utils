package com.jiuwan.utils

import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


@Suppress("DEPRECATION")
abstract class RoundBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object{
        val TAG=RoundBottomSheetDialogFragment::class.java.canonicalName
    }

    var myView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(layoutId(), container, false)
        return myView
    }

    abstract fun layoutId():Int

    abstract fun initWhenViewCreated()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWhenViewCreated()
    }

    //圆角
    /*override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog;
    }*/


    override fun onResume() {
        super.onResume()
        /*val window: Window? = dialog!!.window ?: return
        window ?: return
        val size = Point()

        val display: Display? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                context?.display
            else window?.windowManager?.defaultDisplay
        display ?: return
        display.getSize(size)

        val width: Int = size.x

        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)*/
    }

}