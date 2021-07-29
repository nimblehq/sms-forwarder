package co.nimblehq.smsforwarder.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val sender: String,
    val template: String,
    val forwardEmailAddress: String,
    val forwardSlackChannel: String
) : Parcelable
