package com.jiuwan.utils.download

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jiuwan.platformapp.utils.showToast
import com.jiuwan.utils.R
import com.jiuwan.utils.databinding.ActivityDownloadBinding
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import java.lang.Exception
import kotlin.jvm.Throws


class DownloadActivity:AppCompatActivity() {

    lateinit var binding: ActivityDownloadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDownloadBinding>(this, R.layout.activity_download).also { binding=it }
        try {
            chong()
        }catch (e:Exception){
            showToast(e.message?:return)
        }

    }

    val data1="http://mhhy.dl.gxpan.cn/apk/ml/MBGYD092101/Gardenscapes-ledou-MBGYD092101.apk"
    val data2="hhttp://p.gdown.baidu.com/2c94946f0e26ae462034226fde89692a269f6bdf2aa6e9c8767d772124c9f7c7d260e6931fef78f570c881e16b38ef5c207865+" +
            "2c803b022fe844fb87d68c894ddbef8a9694a05f1e0ff373231ca712808c4440578d04145cdba6bc15f63a58840b47586cd429087098129921afed1197f1295b0f2cf49ff925+" +
            "0a04c0d4ef1ae92fdb5712c98e32f6be819bd5d37ee48e968923ef76a8cc7fb9742033e72b07c84dd4e14dc8d7931011e1242b1a8672969+" +
            "48cf07eaea2a39a3dfd57541fe83eade2e5b448b9ba397e0a84fc90bc2405b969c9634da0803afc6b0464e4a7e333bb0c7072bd418e99312b5457043c33f156a13660aba0be6f15"

    @Throws(Exception::class)
    fun chong(){
        val task = DownloadTask.Builder(data2, getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?:throw Exception("external download not mounted"))
            .setFilename("test.apk") // the minimal interval millisecond for callback progress
            .setMinIntervalMillisCallbackProcess(30) // do re-download even if the task has already been completed in the past.
            .setPassIfAlreadyCompleted(false)
            .build()

        task.enqueue(object :DownloadListener{
            override fun taskStart(task: DownloadTask) {
            }

            override fun connectTrialStart(
                task: DownloadTask,
                requestHeaderFields: MutableMap<String, MutableList<String>>
            ) {
            }

            override fun connectTrialEnd(
                task: DownloadTask,
                responseCode: Int,
                responseHeaderFields: MutableMap<String, MutableList<String>>
            ) {
            }

            override fun downloadFromBeginning(
                task: DownloadTask,
                info: BreakpointInfo,
                cause: ResumeFailedCause
            ) {
            }

            override fun downloadFromBreakpoint(task: DownloadTask, info: BreakpointInfo) {
            }

            override fun connectStart(
                task: DownloadTask,
                blockIndex: Int,
                requestHeaderFields: MutableMap<String, MutableList<String>>
            ) {
            }

            override fun connectEnd(
                task: DownloadTask,
                blockIndex: Int,
                responseCode: Int,
                responseHeaderFields: MutableMap<String, MutableList<String>>
            ) {
            }

            override fun fetchStart(task: DownloadTask, blockIndex: Int, contentLength: Long) {
            }

            override fun fetchProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long) {

            }

            override fun fetchEnd(task: DownloadTask, blockIndex: Int, contentLength: Long) {
            }

            override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
            }

        })
    }
}