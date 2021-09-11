package uz.pdp.pdpapp.kurslar

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.Spin_Adapter_Group
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.classes.StudentsClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentKursAddBinding

class Kurs_Add_Fragment : Fragment(R.layout.fragment_kurs_add) {

    private val binding by viewBinding(FragmentKursAddBinding::bind)
    lateinit var course: GroupClass
    lateinit var student: StudentsClass
    lateinit var dbHelper: MyDbHelper
    lateinit var list: List<Group>
    lateinit var spinnerAdapterGroup: Spin_Adapter_Group
    private var boolean: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_add") as GroupClass
            student = it.getSerializable("student") as StudentsClass
            boolean = it.getBoolean("boolean")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())

        if (boolean!!) {
            list = ArrayList(dbHelper.getListIsOpenGroup_courseID(-1, course.id))
            spinnerAdapterGroup = Spin_Adapter_Group(list)
            binding.spinGroupEdit.adapter = spinnerAdapterGroup
        }

        binding.apply {

            layoutDate.setOnClickListener {
                val dialog = DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth -> date.text = "$dayOfMonth/$month/$year" },
                    2021,
                    8,
                    19
                )

                dialog.show()
            }

            backBtn.setOnClickListener { findNavController().popBackStack() }

            if (!boolean!!) {
                spinCourse.visibility = View.GONE
                if (student.id != -1) {
                    lastname.setText(student.surname)
                    name.setText(student.name)
                    fatherName.setText(student.lastname)
                    date.text = student.time
                }
            }

            save.setOnClickListener {

                if (!boolean!! && student.id != -1) {
                    val surname = lastname.text.toString()
                    val name = name.text.toString()
                    val lastname = fatherName.text.toString()
                    val date = date.text.toString()

                    if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty() && date.isNotEmpty()) {
                        val student1 = StudentsClass(
                            id = student.id,
                            name = name,
                            surname = surname,
                            lastname = lastname,
                            time = date,
                            group = student.group,
                            mentor = student.mentor
                        )
                        dbHelper.editStudent(student1)
                        findNavController().popBackStack()
                    } else Toast.makeText(
                        requireContext(),
                        "Maydon bo`sh",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!boolean!!) {
                    val surname = lastname.text.toString()
                    val name = name.text.toString()
                    val lastname = fatherName.text.toString()
                    val date = date.text.toString()

                    if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty() && date.isNotEmpty()) {
                        val student = StudentsClass(
                            name = name,
                            surname = surname,
                            lastname = lastname,
                            time = date,
                            group = student.group,
                            mentor = student.mentor
                        )
                        dbHelper.addStudent(student)
                        findNavController().popBackStack()
                    } else Toast.makeText(
                        requireContext(),
                        "Maydon bo`sh",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    if (list.isEmpty()) {
                        Toast.makeText(requireContext(), "Guruh qo`shilmagan", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val surname = lastname.text.toString()
                        val name = name.text.toString()
                        val lastname = fatherName.text.toString()
                        val date = date.text.toString()
                        val group = list[spinGroupEdit.selectedItemPosition]

                        val any =
                            if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty() && date.isNotEmpty() && group.name.isNotEmpty()) {
                                val mentor = group.mentor
                                val student = StudentsClass(
                                    name = name,
                                    surname = surname,
                                    lastname = lastname,
                                    time = date,
                                    group = group,
                                    mentor = mentor
                                )
                                dbHelper.addStudent(student)
                                findNavController().popBackStack(R.id.coursListFragment, false)
                            } else Toast.makeText(
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