package uz.pdp.pdpapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.pdp.pdpapp.groups.GroupItemPagerFragment

class ViewPagerAdapter(fm: FragmentManager, var list: List<Int>, var n: Int): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment {
        return GroupItemPagerFragment.newInstance(list[position], n)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position==0) "Ochilganlar"
        else "Ochilayotganlar"
    }
}