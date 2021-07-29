package co.nimblehq.smsforwarder.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val SMS_FILTERING_TABLE_NAME = "Filters"

@Entity(tableName = "Filters")
data class FilterDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "sender")
    val sender: String?,
    @ColumnInfo(name = "template")
    val template: String?,
    @ColumnInfo(name = "forward_email")
    val forwardEmailAddress: String?,
    @ColumnInfo(name = "forward_slack_channel")
    val forwardSlackChannel: String?
)