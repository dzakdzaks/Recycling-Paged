package com.dzakdzaks.recyclingpaged

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dzakdzaks.recyclingpaged.data.MyDataFactory
import com.dzakdzaks.recyclingpaged.data.MyDataSource
import com.dzakdzaks.recyclingpaged.data.model.NowPlayingMovie
import com.utsman.recycling.paged.extentions.NetworkState

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 24 February 2020 at 18:11.
 * Project Name => RecyclingPaged
 * Package Name => com.dzakdzaks.recyclingpaged
 * ==================================//==================================
 * ==================================//==================================
 */

class MyViewModel : ViewModel() {

    private var pagingDataFactory: MyDataFactory? = null

    private fun configPaged(size: Int): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(size)
        .setInitialLoadSizeHint(size * 2)
        .setEnablePlaceholders(true)
        .build()

    fun getMovies(): LiveData<PagedList<NowPlayingMovie>> {
        pagingDataFactory = MyDataFactory()
        return LivePagedListBuilder(pagingDataFactory!!, configPaged(4)).build()
    }

    fun getLoader(): LiveData<NetworkState> = Transformations.switchMap<MyDataSource, NetworkState>(
        pagingDataFactory?.pagingLiveData!!
    ) { it.networkState }

}