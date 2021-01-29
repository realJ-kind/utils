package com.jiuwan.platformapp.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.realJKind.utils.BuildConfig
import java.util.*

object ContextExts {
}

fun Context.showToast(s:String,duration:Int=Toast.LENGTH_SHORT)=Toast.makeText(this,s,duration).show()

internal fun Context.showToastIfDebug(s:String,
                                      duration:Int=Toast.LENGTH_SHORT){
    if(BuildConfig.DEBUG) Toast.makeText(this,s,duration).show()
}

fun _readIniValue(context: Context?, fileName: String, key: String): String {
    val properties = Properties()
    context?.apply {

        properties.load(this.assets.open(fileName))
    }
    return properties[key]?.toString() ?: ""
}

fun Context.getColorCompat(@ColorRes id:Int)=ContextCompat.getColor(this,id)

fun Context.readIniValue( fileName: String, key: String): String {
    val properties = Properties()
        properties.load(this.assets.open(fileName))
    return properties[key]?.toString() ?: ""
}