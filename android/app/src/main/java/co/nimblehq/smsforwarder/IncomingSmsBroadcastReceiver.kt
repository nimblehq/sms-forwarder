package co.nimblehq.smsforwarder

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.persistence.IncomingSmsPersistence
import co.nimblehq.smsforwarder.domain.usecase.NotifyNewIncomingSmsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IncomingSmsBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notifyNewIncomingSmsUseCase: NotifyNewIncomingSmsUseCase

    @Inject
    lateinit var persistence: IncomingSmsPersistence

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val displayOriginatingAddress = messages.first().displayOriginatingAddress
        // Concat the list of displayMessageBody to single message
        val displayMessageBody = messages.joinToString("") { sms -> sms.displayMessageBody }

        // TODO Use UseCase instead
        persistence.newIncomingSms(
            IncomingSmsEntity(
                displayOriginatingAddress,
                displayMessageBody
            )
        )
    }
}
