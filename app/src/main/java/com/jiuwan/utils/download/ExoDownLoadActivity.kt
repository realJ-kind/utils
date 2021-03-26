package com.jiuwan.utils.download

import android.media.browse.MediaBrowser
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jiuwan.utils.R
import com.jiuwan.utils.databinding.ActivityExoDownloadBinding
import com.liulishuo.okdownload.OkDownloadProvider.context


class ExoDownLoadActivity : AppCompatActivity() {

    lateinit var player: SimpleExoPlayer
    lateinit var binding: ActivityExoDownloadBinding

    val uri by lazy {
        Uri.parse("https://vod-progressive.akamaized.net/exp=1616473370~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3494%2F13%2F342471925%2F1369356711.mp4~hmac=313664ca27f7ac1ff468b76441fc0d403cbfb8f920da788e2a3970fdcd601e3b/vimeo-prod-skyfire-std-us/01/3494/13/342471925/1369356711.mp4?download=1&filename=Pexels+Videos+2495382.mp4")
    }

    val video by lazy {
        MediaItem.fromUri(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_exo_download)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exo_download)

        player = SimpleExoPlayer.Builder(context).build().apply {
            binding.videoView.player=this
            setMediaItem(video)
            prepare()
            play()
        }

    }

}