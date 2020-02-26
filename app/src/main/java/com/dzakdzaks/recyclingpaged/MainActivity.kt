package com.dzakdzaks.recyclingpaged

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakdzaks.recyclingpaged.data.model.NowPlayingMovie
import com.utsman.recycling.paged.setupAdapterPaged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        initBottomBar()
        initRecycler(viewModel)
    }

    private fun initRecycler(viewModel: MyViewModel) {
        main_recycler_view.setupAdapterPaged<NowPlayingMovie>(R.layout.item_movie) { adapter, context, list ->

            bind { itemView, position, item ->
                itemView.textMovieTitle.text = item?.title
                itemView.textMovieOverview.text = item?.overview
                itemView.textMovieReleaseDate.text = dateFormater(item?.releaseDate!!)
                itemView.textMovieVoteAverage.text = "${item.voteAverage.toString()} / 10"

                val imgPosterUrl = IMAGE_URL_POSTER + item.posterPath
                val imgBGUrl = IMAGE_URL_BACKDROP + item.backdropPath

                logi("imagePoster --> $imgPosterUrl")
                logi("imageBG --> $imgBGUrl")

                itemView.imageMovie.loadImage(imgPosterUrl)
                itemView.imageMovieBg.loadImage(imgBGUrl)

                itemView.cardCategory.setOnClickListener {
                    toast("Click on $position of ${adapter.itemCount}")
                }

            }

            val layoutManager = LinearLayoutManager(context)
            setLayoutManager(layoutManager)

            addLoader(R.layout.item_loader) {
                idLoader = R.id.progress_circular
                idTextError = R.id.error_text_view
            }

            viewModel.getMovies().observe(context as LifecycleOwner, Observer {
                submitList(it)
            })

            viewModel.getLoader().observe(context as LifecycleOwner, Observer {
                submitNetwork(it)
            })

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initBottomBar() {
        color.setBackgroundColor(ColorUtils.setAlphaComponent(Color.parseColor("#FF8888"), 60))

        expandable_bottom_bar.onItemSelectedListener = { v, item ->
            val anim = ViewAnimationUtils.createCircularReveal(
                color,
                expandable_bottom_bar.x.toInt() + v.x.toInt() + v.width / 2,
                expandable_bottom_bar.y.toInt() + v.y.toInt() + v.height / 2, 0F,
                findViewById<View>(android.R.id.content).height.toFloat()
            )
            color.setBackgroundColor(ColorUtils.setAlphaComponent(item.activeColor, 60))
            anim.duration = 420
            anim.start()
        }
    }
}
