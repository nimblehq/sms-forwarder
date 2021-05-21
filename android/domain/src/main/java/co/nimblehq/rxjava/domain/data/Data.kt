package co.nimblehq.smsforwarder.domain.data

import android.os.Parcelable
import co.nimblehq.smsforwarder.data.service.response.ExampleResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val title: String,
    val author: String,
    val thumbnail: String,
    val url: String
) : Parcelable

fun ExampleResponse.toDataList() =
    data.children.map {
        Data(
            it.data.title,
            it.data.author,
            it.data.thumbnail,
            it.data.url
        )
    }
