package com.dangerfield.kind.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dangerfield.kind.R
import kotlinx.android.synthetic.main.fragment_main.*
import com.dangerfield.kind.MainPagerAdapter as MainPagerAdapter1


class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        pager.adapter = MainPagerAdapter1(childFragmentManager)
        pager.currentItem = 1 //so it starts off at feed
        pager.offscreenPageLimit = 2 //lets fragments remain in memory
        am_snap_tabs.initViewPager(pager)
    }
}
