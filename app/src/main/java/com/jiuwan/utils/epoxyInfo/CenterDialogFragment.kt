package com.jiuwan.utils.epoxyInfo

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.jiuwan.utils.R

abstract class CenterDialogFragment :DialogFragment(){
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

   /* override fun getTheme(): Int {
        return R.style.Theme_Overlay_materialDialog_CenterDialogFragment
    }*/


    override fun onResume() {
        super.onResume()
        val window: Window? = dialog!!.window ?: return
        window ?: return
        val size = Point()

        val display: Display? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                context?.display
            else window?.windowManager?.defaultDisplay
        display ?: return
        display.getSize(size)

        val width: Int = size.x

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        context?: return
        window.setBackgroundDrawable(ContextCompat.getDrawable(context!!,R.drawable.bg_center_dialog_rounded))
    }
}