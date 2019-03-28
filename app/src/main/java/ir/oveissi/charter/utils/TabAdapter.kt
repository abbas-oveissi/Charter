package ir.oveissi.charter.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ir.oveissi.charter.features.syncdashboard.SyncDashboardFragment
import ir.oveissi.charter.features.ticketpricelist.TicketPriceListFragment


const val TAB_SIZE = 2
val TAB_TITLE = listOf("Sync Dashboard", "History")

class TabAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return SyncDashboardFragment()
        return TicketPriceListFragment()
    }

    override fun getCount() = TAB_SIZE

    override fun getPageTitle(position: Int) = TAB_TITLE[position]

}