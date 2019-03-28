package ir.oveissi.charter.sync

import androidx.work.*
import ir.oveissi.charter.utils.Constants
import java.util.concurrent.TimeUnit

class WorkRequestFactory {

    companion object {
        fun createGetTicketPriceRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            return PeriodicWorkRequest.Builder(GetTicketPriceWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        }


        fun createForceGetTicketPriceRequest(): OneTimeWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val data = Data.Builder()
                .putBoolean(Constants.KEY_IS_FORCE,true)
                .build()

            return OneTimeWorkRequest.Builder(GetTicketPriceWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
        }
    }
}