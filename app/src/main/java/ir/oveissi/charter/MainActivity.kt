package ir.oveissi.charter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ir.oveissi.charter.utils.TabAdapter

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var tabs: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        setupToolbar()
        setupTabs()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupTabs() {
        val tabsAdapter = TabAdapter(supportFragmentManager)
        viewPager.adapter = tabsAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun findViews() {
        toolbar = findViewById(R.id.toolbar)
        tabs = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)
    }


}
