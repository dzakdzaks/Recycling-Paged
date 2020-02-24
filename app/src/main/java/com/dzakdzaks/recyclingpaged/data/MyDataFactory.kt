package com.dzakdzaks.recyclingpaged.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dzakdzaks.recyclingpaged.data.model.NowPlayingMovie

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 24 February 2020 at 18:08.
 * Project Name => RecyclingPaged
 * Package Name => com.dzakdzaks.recyclingpaged.data
 * ==================================//==================================
 * ==================================//==================================
 */

class MyDataFactory : DataSource.Factory<Int, NowPlayingMovie>() {

    val pagingLiveData = MutableLiveData<MyDataSource>()
    override fun create(): DataSource<Int, NowPlayingMovie> {
        val data =  MyDataSource()
        pagingLiveData.postValue(data)
        return data
    }

}