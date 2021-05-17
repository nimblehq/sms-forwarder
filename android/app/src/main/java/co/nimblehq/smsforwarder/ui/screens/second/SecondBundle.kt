package co.nimblehq.smsforwarder.ui.screens.second

import android.os.Parcelable
import co.nimblehq.smsforwarder.domain.data.Data
import kotlinx.parcelize.Parcelize

@Parcelize
data class SecondBundle(
    val data: Data
) : Parcelable
