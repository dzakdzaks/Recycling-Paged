package com.dzakdzaks.recyclingpaged

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 24 February 2020 at 17:38.
 * Project Name => RecyclingPaged
 * Package Name => com.dzakdzaks.recyclingpaged
 * ==================================//==================================
 * ==================================//==================================
 */

fun logi(msg: String) = Log.i("anjay", msg)

fun loge(msg: String) = Log.e("anjay", msg)

fun ImageView.loadImage(url: String?) = Glide.with(this.context).load(url).into(this)
fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "a1821872f81f15ac6148ba0e0b625bbd"
const val HEADER =
    "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZmMyNDhlYjc0Mzk4M2Q3ZWY3NzUxM2QwOGQ2ZTMxMjdkYWQ4NmQ3NzBlNGYxMjk4ZWIzZTlmODUzOTFmYTdmZmFiNzQ5ODVlMjYyMzU1MTMiLCJpYXQiOjE1ODA4MTA1MjEsIm5iZiI6MTU4MDgxMDUyMSwiZXhwIjoxNjEyNDMyOTIxLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.D5jKs6Pc3zRiTvNT-QinQlzzUyn4PoD7taTEp7tBu9erqotU8lXfqbH0HYvX7Vo19n0O6kNSvtpqDEOLwIrjsXyyu0uP0HNjgUaOj9oVmxHmXJfpouL4zYAAMZ-YsRC4XiTA_cpRlZ0ndn_dSZn9RsCB9M93cMudp2oDtEoes7nlqSCMgIlsOMrrVxITXS0UPVYY_b5i7eet9tHdayJJAcgPg9KHIOhTV0Lr4Eru_jeSFPq1vkw1qvgqsYiIuhWxXILtuPCANuWuZ43hC3sEwXDy-uLnDqnh_JMwpMr8pTuwo7jPzMvo09FcsqTN1NQIedPCQ7S_t-eJHbyI1oPCaYO4iKfLDePy3rg1xS6vGWHPPMA9S8xzeKfiUNDZUcS-Cm6xqsnO0MAbFeqF9--zLpednH2f8ZF3nZTVsw83khsKPL1fLPeNnHV4ZwzJXBMbuQCbLwnBCKbk-fSFrSSLqEk8-DCSY49xskTEWcsQxgL-Pp3-sv8m60DHp0X1UX97RylO_GV6DdFnPrsZHgsImdwn7wy9LR2J4IZsscao1HiNxQpH1iyJxEC_DK0cDfb-rf7cdYp_9EzpXkIWJkavPTS_qwMRUxwJHeqV2yCJevEh50czk005mekqniTiSZwc5LyuPoSotMO9tRvPpOH3dwdc0E08cwlWO7vbN2RR3VQ"
const val IMAGE_URL_BACKDROP = "http://image.tmdb.org/t/p/w780"
const val IMAGE_URL_POSTER = "http://image.tmdb.org/t/p/w200"

@SuppressLint("SimpleDateFormat")
fun dateFormater(oldDate: String): String? {
    var newDate: String? = null
    val oldFormat = SimpleDateFormat("yyyy-mm-dd")
    val convertedDate: Date?

    try {
        convertedDate = oldFormat.parse(oldDate)
        val newFormat = SimpleDateFormat("MMMM dd, yyyy")
        newDate = newFormat.format(convertedDate!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return newDate
}