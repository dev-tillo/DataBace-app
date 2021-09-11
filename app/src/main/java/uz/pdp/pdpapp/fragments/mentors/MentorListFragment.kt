package uz.pdp.pdpapp.fragments.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.ViewAdapter
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentMentorListBinding


class MentorListFragment : Fragment(R.layout.fragment_mentor_list) {
    private val binding by viewBinding(FragmentMentorListBinding::bind)
    lateinit var dbHelper: MyDbHelper
    lateinit var list: List<GroupClass>
    lateinit var recAdapter: ViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = ArrayList(dbHelper.getListCourse())
        recAdapter = ViewAdapter(list, object : ViewAdapter.OnClickMyListener{
            override fun onClick(course: GroupClass) {
                val bundle = Bundle()
                bundle.putSerializable("course_mentor", course)
                findNavController().navigate(R.id.mentorenterFragment, bundle)
            }
        })

        binding.apply {
            backBtn.setOnClickListener { findNavController().popBackStack() }

            recycle.adapter = recAdapter
        }
    }
}