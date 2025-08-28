package com.utkuaksu.demoapp.ui.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utkuaksu.demoapp.ui.currency.CurrencyFragment
import com.utkuaksu.demoapp.ui.emtia.EmtiaFragment
import com.utkuaksu.demoapp.ui.share.ShareFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        CurrencyFragment(),
        EmtiaFragment(),
        ShareFragment()
    )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}
