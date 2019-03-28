package ir.oveissi.charter.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "ticket_price_table")
data class TicketPrice(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val pathIndex: Int,
    val price: Long,
    val datetime: OffsetDateTime,
    val dateFlight: String,
    val week: String,
    val link: String,
    val is_manual: Boolean = false
)