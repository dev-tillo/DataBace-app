package uz.pdp.pdpapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var dbHelper: MyDbHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        binding.apply {
            course.setOnClickListener {
                findNavController().navigate(R.id.coursListFragment)
            }
            group.setOnClickListener {
                findNavController().navigate(R.id.groupListFragment)
            }
            mentor.setOnClickListener {
                findNavController().navigate(R.id.mentorListFragment)
            }
        }
    }
}