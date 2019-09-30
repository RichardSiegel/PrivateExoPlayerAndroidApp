package work.siegel.homestreamer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPos = 0L
    private val dashUrl = "http://simple3d.de/media/source.mpd"
    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }
    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    constructor(parcel: Parcel) : this() {
        playbackPos = parcel.readLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initializeExoPlayer()
    }

    override fun onStop() {
        releaseExoPlayer()
        super.onStop()
    }

    private fun initializeExoPlayer(){
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(adaptiveTrackSelectionFactory),
            DefaultLoadControl()
        )

        prepareExoPlayer()
        simpleExoPlayerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPos)
        simpleExoplayer.playWhenReady = true
    }

    private fun releaseExoPlayer(){
        playbackPos = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun buildMediaSrc(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("ua",bandwidthMeter)
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null)
    }

    private fun prepareExoPlayer(){
        val uri = Uri.parse(dashUrl)
        val mediaSource = buildMediaSrc(uri)
        simpleExoplayer.prepare(mediaSource)
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

}
