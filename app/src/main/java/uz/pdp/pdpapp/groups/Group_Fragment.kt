package uz.pdp.pdpapp.groups

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.ViewPagerAdapter
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentGroupEnterBinding

class Group_Fragment : Fragment(R.layout.fragment_group_enter) {

    private val binding by viewBinding(FragmentGroupEnterBinding::bind)
    lateinit var course: GroupClass
    lateinit var dbHelper: MyDbHelper
    lateinit var pagerAdapter: ViewPagerAdapter
    lateinit var list: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_group") as GroupClass
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = listOf(1, -1)
        pagerAdapter =
            ViewPagerAdapter(childFragmentManager, list, course.id)

        binding.apply {
            viewPager.adapter = pagerAdapter
            backBtn.setOnClickListener { findNavController().popBackStack() }
            tabLayout.setupWithViewPager(viewPager)
            tabLayout.setTabTextColors(Color.GREEN, Color.RED)


            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        plus.visibility = View.INVISIBLE
                    } else {
                        plus.visibility = View.VISIBLE
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {

                }

            })

            plus.setOnClickListener {
                if (dbHelper.getListMentorByID(course.id).isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putSerializable("course_group", course)
                    findNavController().navigate(R.id.groupAddFragment, bundle)
                } else {
                    Toast.makeText(requireContext(), "Mentor qo`shilmagan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.viewPager.currentItem == 1)
            binding.plus.visibility = View.VISIBLE
    }
}