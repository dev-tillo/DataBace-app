package uz.pdp.pdpapp.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.Spin_Mentor_Adapter
import uz.pdp.pdpapp.adapters.Spin_Time_Adapter
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.classes.MentorClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentGroupAddBinding

class Group_Add_Fragment : Fragment(R.layout.fragment_group_add) {

    private val binding by viewBinding(FragmentGroupAddBinding::bind)
    lateinit var course: GroupClass
    lateinit var dbHelper: MyDbHelper
    lateinit var list: List<MentorClass>
    lateinit var spinnerAdapterMentor: Spin_Mentor_Adapter
    lateinit var spinnerAdapterTime1: Spin_Time_Adapter
    lateinit var spinnerAdapterTime2: Spin_Time_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_group") as GroupClass
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = dbHelper.getListMentorByID(course.id)

        val listTime = listOf("9:00-11:00", "14:00-16:00", "16:00-18:00", "19:00-21:00")
        val listDate = listOf("juft", "toq")
        spinnerAdapterMentor = Spin_Mentor_Adapter(list)
        spinnerAdapterTime1 = Spin_Time_Adapter(listTime)
        spinnerAdapterTime2 = Spin_Time_Adapter(listDate)

        binding.apply {
            backBtn.setOnClickListener { findNavController().popBackStack() }
            spinMentorEdit.adapter = spinnerAdapterMentor
            spinCourseTime.adapter = spinnerAdapterTime1
            spinCourseDate.adapter = spinnerAdapterTime2

            save.setOnClickListener {
                if (list.isEmpty()) {
                    Toast.makeText(requireContext(), "Mentor mavjudamas", Toast.LENGTH_SHORT).show()
                } else {
                    val group_name = groupName.text
                    val mentor = list[spinMentorEdit.selectedItemPosition]
                    val time = listTime[spinCourseTime.selectedItemPosition]
                    val date = listDate[spinCourseDate.selectedItemPosition]

                    if (group_name.isNotEmpty() && mentor.name.isNotEmpty() && time.isNotEmpty() && date.isNotEmpty()) {
                        val group = Group(
                            name = group_name.toString(),
                            isOpen = -1,
                            date = date,
                            time = time,
                            mentor = mentor
                        )
                        dbHelper.addGroup(group)
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Maydon bo`sh",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}