package ir.oveissi.charter.features.ticketpricelist


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.oveissi.charter.App
import ir.oveissi.charter.R
import ir.oveissi.charter.db.TicketPrice
import ir.oveissi.charter.db.TicketPriceDatabase

class TicketPriceListFragment : Fragment() {

    private lateinit var rvTicketPrices: RecyclerView
    private val adapter = TicketPriceAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ticket_price_list, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        findViews(root)
        setupRV()
        setupObserver()
    }

    private fun setupObserver() {
        createDbLiveData()?.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupRV() {
        rvTicketPrices.layoutManager = LinearLayoutManager(activity as Context)
        rvTicketPrices.adapter = adapter
        rvTicketPrices.addItemDecoration(DividerItemDecoration(activity as Context, DividerItemDecoration.VERTICAL))
    }

    private fun findViews(root: View) {
        rvTicketPrices = root.findViewById(R.id.rvTicketPrices)
    }


    private fun createDbLiveData(): LiveData<PagedList<TicketPrice>>? {
        val database = TicketPriceDatabase.getInstance(App.appContext) ?: return null
        val factory: DataSource.Factory<Int, TicketPrice> = database.ticketPriceDao().getAll()
        return factory.toLiveData(50)
    }
}
