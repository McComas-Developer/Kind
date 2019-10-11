package com.dangerfield.kind


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewPager()
        Log.d("Navigation","SHOWING MAIN FRAGMENT WITH VIEW PAGER")

    }

    private fun setupViewPager() {
        pager.adapter = MainPagerAdapter(childFragmentManager)
        pager.currentItem = 1 //so it starts off at feed
        pager.offscreenPageLimit = 2 //lets fragments remain in memory
        am_snap_tabs.initViewPager(pager)
    }
}
