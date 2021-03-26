package com.jiuwan.utils.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jiuwan.platformapp.utils.getColorCompat
import com.jiuwan.utils.R
import com.jiuwan.utils.databinding.ActivityScrollingBinding

fun Context.makeColorDrawble(@ColorRes id: Int)= ColorDrawable(getColorCompat(id))

class ScrollingActivity : AppCompatActivity() {

    val stickyView
    get() =   findViewById<View>(R.id.sticky_view)

    lateinit var binding:ActivityScrollingBinding


    val toolbar
    get() = binding.toolbar

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_scrolling)
        binding.apply { lifecycleOwner=this@ScrollingActivity;resetCollapsedHieght=true}


    /*    binding.toolbar.background=makeColorDrawble(android.R.color.transparent)*/

  /*      AppBarLayout.ScrollingViewBehavior()*/
       /* findViewById<AppBarLayout>(R.id.app_bar).addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Log.e(TAG, "top:${top},bottom:${bottom}")
            Log.e(TAG, "transY:${v.translationY}")
        }

        findViewById<AppBarLayout>(R.id.app_bar).setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.e(TAG, "scrollY:${scrollY}")
        }
        findViewById<NestedScrollView>(R.id.scroll_v).setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.e(TAG, "transY:${findViewById<AppBarLayout>(R.id.app_bar).translationY}")
        }*/

        stickyView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN



    }

    val TAG="SCRLLING"
}

object NoopWindowInsetsListener : View.OnApplyWindowInsetsListener {
    override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
        return insets
    }
}

object HeightTopWindowInsetsListener : View.OnApplyWindowInsetsListener {
    override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
        val topInset = insets.systemWindowInsetTop
        if (v.layoutParams.height != topInset) {
            v.layoutParams.height = topInset
            v.requestLayout()
        }
        return insets
    }
}

/**
 * Updates this view's padding. This version of the method allows using named parameters
 * to just set one or more axes.
 *
 * @see View.setPadding
 */
inline fun View.updatePadding(
        @Px left: Int = paddingLeft,
        @Px top: Int = paddingTop,
        @Px right: Int = paddingRight,
        @Px bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.doOnApplyWindowInsets(f: (View, WindowInsetsCompat, ViewPaddingState) -> Unit) {
    // Create a snapshot of the view's padding state
    val paddingState = createStateForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        f(v, insets, paddingState)
        insets
    }
    requestApplyInsetsWhenAttached()
}

/**
 * Call [View.requestApplyInsets] in a safe away. If we're attached it calls it straight-away.
 * If not it sets an [View.OnAttachStateChangeListener] and waits to be attached before calling
 * [View.requestApplyInsets].
 */
fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

private fun createStateForView(view: View) = ViewPaddingState(view.paddingLeft,
        view.paddingTop, view.paddingRight, view.paddingBottom, view.paddingStart, view.paddingEnd)

data class ViewPaddingState(
        val left: Int,
        val top: Int,
        val right: Int,
        val bottom: Int,
        val start: Int,
        val end: Int
)

fun View.safeClick(interval:Long =1500L, action: (View) -> Unit){
    var lastClick=0L
    setOnClickListener {
        val now =System.currentTimeMillis()
        val passed =now -lastClick
        if(passed<interval) return@setOnClickListener
        lastClick=now
        action.invoke(it)
    }
}

fun EditText.moveCursorToEnd()=setSelection(getText().length)

@BindingAdapter("resetCollapsedHieght")
fun resetCollapsedHieght(constraintLayout: ConstraintLayout, resetMinHeight: Boolean) {
    if (resetMinHeight) {
        val stickyHeader: View = constraintLayout.findViewById<View>(R.id.sticky_view)

        val toolbar = constraintLayout.rootView.findViewById<View>(R.id.toolbar)
        val nestedScrollView = constraintLayout.rootView.findViewById<View>(R.id.scroll_v)
        val appBarLayout = constraintLayout.rootView.findViewById<View>(R.id.app_bar)
        val appBarLayoutContent = appBarLayout.findViewById<View>(R.id.app_bar_content)
        toolbar.doOnApplyWindowInsets { view, windowInsetsCompat, viewPaddingState ->
            val should=windowInsetsCompat.systemWindowInsetTop
            val now =viewPaddingState.top
            val change=should-now
            view.updatePadding(top=now+change)
        }
    }
}


object Utils{
    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }
}


