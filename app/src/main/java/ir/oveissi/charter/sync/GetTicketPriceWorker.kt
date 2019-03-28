package ir.oveissi.charter.sync

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ir.oveissi.charter.utils.Constants
import ir.oveissi.charter.utils.PathEnum
import ir.oveissi.charter.api.Api
import ir.oveissi.charter.db.TicketPrice
import ir.oveissi.charter.db.TicketPriceDatabase
import org.threeten.bp.OffsetDateTime


class GetTicketPriceWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val pathIndex = inputData.getInt(Constants.KEY_PATH_INDEX, 0)
        val isForce = inputData.getBoolean(Constants.KEY_IS_FORCE, false)
        val tp = Api.getMinTp(PathEnum.values()[pathIndex])
        if (tp != null) {
            val tpDao = TicketPriceDatabase.getInstance(context)?.ticketPriceDao()
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
                        isForce
                    )
                it.insert(tempTicketPrice)
            }
            return Result.success()
        }
        return Result.failure()
    }

}