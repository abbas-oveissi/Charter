package ir.oveissi.charter.features.syncdashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import ir.oveissi.charter.R
import ir.oveissi.charter.db.TicketPriceDatabase
import ir.oveissi.charter.sync.WorkRequestFactory
import ir.oveissi.charter.utils.Constants
import ir.oveissi.charter.utils.ForceDataSync
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class SyncDashboardFragment : Fragment() {

    lateinit var tvPrice: TextView
    lateinit var tvDate: TextView

    lateinit var btnStart: Button
    lateinit var btnStop: Button
    lateinit var btnGetData: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sync_dashboard, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        findviews(root)
        setListeners()
        setObserver()
    }

    private fun findviews(root: View) {
        btnGetData = root.findViewById(R.id.btnGetData)
        tvPrice = root.findViewById(R.id.tvPrice)
        tvDate = root.findViewById(R.id.tvDate)
        btnStart = root.findViewById(R.id.btnStartService)
        btnStop = root.findViewById(R.id.btnStopService)
    }

    private fun setObserver() {
        TicketPriceDatabase.getInstance(activity as Context)?.ticketPriceDao()
            ?.lastTicketPrice()
            ?.observe(this, Observer { ticketPrice ->
                if (ticketPrice != null) {
                    val money =
                        Constants.MONEY_FORMATTER.format(Constants.MONEY_FORMATTER.parse(ticketPrice.price.toString())) + " تومان"
                    tvPrice.text = ticketPrice.dateFlight + " - " + ticketPrice.week + " : " + money
                    tvDate.text = ticketPrice.datetime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
                }

            })

        WorkManager.getInstance().getWorkInfosForUniqueWorkLiveData(Constants.UNIQUE_PRIODIC_SYNC)
            .observe(this, Observer { workInfos ->
                if (workInfos != null && workInfos.size > 0) {
                    val workInfo = workInfos.first()
                    if (workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING) {
                        btnStart.isEnabled = false
                        btnStop.isEnabled = true
                    } else {
                        btnStart.isEnabled = true
                        btnStop.isEnabled = false
                    }
                } else {
                    btnStart.isEnabled = true
                    btnStop.isEnabled = false
                }
            })
    }

    private fun setListeners() {

        btnGetData.setOnClickListener {
            ForceDataSync().execute()

            WorkManager.getInstance().enqueueUniqueWork(
                Constants.UNIQUE_FORCE_SYNC,
                ExistingWorkPolicy.KEEP,
                WorkRequestFactory.createForceGetTicketPriceRequest()
            )
        }


        btnStart.setOnClickListener {
            WorkManager.getInstance().enqueueUniquePeriodicWork(
                Constants.UNIQUE_PRIODIC_SYNC,
                ExistingPeriodicWorkPolicy.KEEP,
                WorkRequestFactory.createGetTicketPriceRequest()
            )
        }

        btnStop.setOnClickListener {
            WorkManager.getInstance().cancelUniqueWork(Constants.UNIQUE_PRIODIC_SYNC)
        }
    }
}
