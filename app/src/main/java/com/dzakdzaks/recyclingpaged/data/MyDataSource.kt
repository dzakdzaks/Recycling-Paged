package com.dzakdzaks.recyclingpaged.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.dzakdzaks.recyclingpaged.API_KEY
import com.dzakdzaks.recyclingpaged.RetrofitInstance
import com.dzakdzaks.recyclingpaged.data.model.NowPlayingMovie
import com.dzakdzaks.recyclingpaged.data.model.ResponseNowPlayingMovies
import com.dzakdzaks.recyclingpaged.loge
import com.dzakdzaks.recyclingpaged.logi
import com.utsman.recycling.paged.extentions.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 24 February 2020 at 17:32.
 * Project Name => RecyclingPaged
 * Package Name => com.dzakdzaks.recyclingpaged.data
 * ==================================//==================================
 * ==================================//==================================
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyDataSource : ItemKeyedDataSource<Int, NowPlayingMovie>() {

    private var page = 1
    var networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<NowPlayingMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        RetrofitInstance.create().getNowPlayingMovies(API_KEY, page)
            .enqueue(object : Callback<ResponseNowPlayingMovies> {
                override fun onFailure(call: Call<ResponseNowPlayingMovies>, t: Throwable) {
                    loge(t.localizedMessage)
                    networkState.postValue(NetworkState.error(t.localizedMessage))
                }

                override fun onResponse(
                    call: Call<ResponseNowPlayingMovies>,
                    response: Response<ResponseNowPlayingMovies>
                ) {
                    val body = response.body()
                    if (body != null) {
                        page++
                        logi("movies -> ${body.results}")
                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(body.results!!)
                    }
                }

            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<NowPlayingMovie>) {
        networkState.postValue(NetworkState.LOADING)
        RetrofitInstance.create().getNowPlayingMovies(API_KEY, page)
            .enqueue(object : Callback<ResponseNowPlayingMovies> {
                override fun onFailure(call: Call<ResponseNowPlayingMovies>, t: Throwable) {
                    loge(t.localizedMessage)
                    networkState.postValue(NetworkState.error(t.localizedMessage))
                }

                override fun onResponse(
                    call: Call<ResponseNowPlayingMovies>,
                    response: Response<ResponseNowPlayingMovies>
                ) {
                    val body = response.body()
                    if (body != null) {
                        page++
                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(body.results!!)
                    }
                }

            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<NowPlayingMovie>) {}

    override fun getKey(item: NowPlayingMovie): Int = item.id!!

}