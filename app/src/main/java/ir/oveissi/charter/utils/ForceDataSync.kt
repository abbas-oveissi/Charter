package ir.oveissi.charter.utils

import android.os.AsyncTask
import ir.oveissi.charter.App
import ir.oveissi.charter.api.Api
import ir.oveissi.charter.db.TicketPrice
import ir.oveissi.charter.db.TicketPriceDatabase
import org.threeten.bp.OffsetDateTime


class ForceDataSync() : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        val tp = Api.getMinTp(PathEnum.KISH_TEHRAN)
        if (tp != null) {
            val tpDao = TicketPriceDatabase.getInstance(App.appContext)?.ticketPriceDao()
            tpDao?.let {
                val tempTicketPrice =
                    TicketPrice(
                        null,
                        PathEnum.KISH_TEHRAN.ordinal,
                        tp.price.toLong(),
                        OffsetDateTime.now(),
                        tp.date_flight,
                        tp.week,
                        tp.link,
                        true
                    )
                it.insert(tempTicketPrice)
            }
        }
        return null
    }

}