package uz.pdp.pdpapp.kurslar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.classes.StudentsClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentKursAboutBinding

class Kurs_About_Fragment : Fragment(R.layout.fragment_kurs_about) {

    private val binding by viewBinding(FragmentKursAboutBinding::bind)
    lateinit var course: GroupClass
    lateinit var dbHelper: MyDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course") as GroupClass
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())

        binding.apply {
            courseName.text = "${course.name} Development"

            description.text = course.description

            backBtn.setOnClickListener { findNavController().popBackStack() }

            addStudent.setOnClickListener {
                if (dbHelper.getLisIsOpenGroup(-1).isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putSerializable("course_add", course)
                    bundle.putBoolean("boolean", true)
                    bundle.putSerializable("student", StudentsClass(-1, "", "","","", dbHelper.getLisIsOpenGroup(-1)[0], dbHelper.getLisIsOpenGroup(-1)[0].mentor))
                    findNavController().navigate(R.id.courseAddFragment, bundle)
                } else Toast.makeText(requireContext(), "${course.name}ga grouppa qushilmagan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}