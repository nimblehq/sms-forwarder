package co.nimblehq.smsforwarder.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sms(
    val title: String,
    val author: String,
    val thumbnail: String,
    val url: String
) : Parcelable
