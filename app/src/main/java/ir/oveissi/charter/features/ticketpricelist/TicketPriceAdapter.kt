package ir.oveissi.charter.features.ticketpricelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.oveissi.charter.db.TicketPrice
import ir.oveissi.charter.utils.Constants
import org.threeten.bp.format.DateTimeFormatter
import java.text.DecimalFormat
import android.R
import org.threeten.bp.format.FormatStyle


class TicketPriceAdapter() : PagedListAdapter<TicketPrice, TicketPriceAdapter.ViewHolder>(TicketPriceDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(ir.oveissi.charter.R.layout.row_ticket_price, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        val tvPrice = root.findViewById<TextView>(ir.oveissi.charter.R.id.tvPrice)
        val tvDate = root.findViewById<TextView>(ir.oveissi.charter.R.id.tvDate)
        val tvTitle = root.findViewById<TextView>(ir.oveissi.charter.R.id.tvTitle)

        fun bind(ticketPrice: TicketPrice?) {
            ticketPrice?.let {
                tvPrice.text = Constants.MONEY_FORMATTER.format(Constants.MONEY_FORMATTER.parse(ticketPrice.price.toString())) + " تومان"
                tvDate.text = ticketPrice.datetime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
                tvTitle.text = ticketPrice.dateFlight + " - " + ticketPrice.week
            }
        }
    }

    class TicketPriceDiffCallback : DiffUtil.ItemCallback<TicketPrice>() {

        override fun areItemsTheSame(oldItem: TicketPrice, newItem: TicketPrice): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketPrice, newItem: TicketPrice): Boolean {
            return oldItem == newItem
        }
    }
}