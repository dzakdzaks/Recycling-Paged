package com.dzakdzaks.recyclingpaged

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
}
