package uz.pdp.pdpapp.fragments.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.classes.MentorClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentMentorAboutBinding

class Mentor_Add_Fragment : Fragment(R.layout.fragment_mentor_about) {
    private val binding by viewBinding(FragmentMentorAboutBinding::bind)
    lateinit var dbHelper: MyDbHelper
    lateinit var course: GroupClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_add_mentor") as GroupClass
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())

        binding.apply {
            backBtn.setOnClickListener { findNavController().popBackStack() }

            save.setOnClickListener {
                if (lastname.text.isNotEmpty() && name.text.isNotEmpty() && fatherName.text.isNotEmpty()) {
                    val mentor = MentorClass(
                        name = name.text.toString(),
                        surname = lastname.text.toString(),
                        lastname = fatherName.text.toString(),
                        course = course
                    )
                    dbHelper.addMentor(mentor)
                    findNavController().popBackStack()
                } else Toast.makeText(
                    requireContext(),
                    "Maydon bo`sh",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}