package uz.pdp.pdpapp.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.ViewPager3Adapter
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.classes.StudentsClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentGroupAboutBinding


class Group_About_Fragment : Fragment(R.layout.fragment_group_about) {

    private val binding by viewBinding(FragmentGroupAboutBinding::bind)
    lateinit var dbHelper: MyDbHelper
    lateinit var recAdapter3: ViewPager3Adapter
    lateinit var list: ArrayList<StudentsClass>
    lateinit var group: Group
    lateinit var student1: StudentsClass
    private var size = 0
    var isDelete: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            group = it.getSerializable("group") as Group
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = ArrayList(dbHelper.getListStudentByID(group.id))
        student1 = StudentsClass(0, "", "", "", "", group, group.mentor)
        recAdapter3 = ViewPager3Adapter(false, list, object : ViewPager3Adapter.OnClickMyListener {

            override fun onClickDelete(student: StudentsClass, position: Int) {
                student1 = student
                val snackbar = Snackbar.make(requireView(), "delete talaba", Snackbar.LENGTH_LONG)
                snackbar.setAction("Qaytish") {
                    list.add(position, student)
                    isDelete = false
                    recAdapter3.notifyItemInserted(position)
                }
                snackbar.show()
                list.removeAt(position)
                recAdapter3.notifyItemRemoved(position)
                recAdapter3.notifyItemChanged(position)
                isDelete = true
            }

            override fun onClickEdit(student: StudentsClass, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("course_add", group.mentor.course)
                bundle.putSerializable("student", student)
                bundle.putBoolean("boolean", false)
                findNavController().navigate(R.id.courseAddFragment, bundle)
            }

        })

        binding.apply {

            recycle.adapter = recAdapter3
            groupTxt.text = group.name
            timeTxt.text = "vaqti: ${group.time}"
            mentorTxt.text = "mentor: ${group.mentor.name}"

            if (group.isOpen == 1) {
                started.visibility = View.INVISIBLE
            }

            started.setOnClickListener  {
                if (group.isOpen == -1 && list.size >= 3) {
                    group.isOpen = 1
                    dbHelper.editGroup(group)
                    Toast.makeText(
                        requireContext(),
                        "Muvaffaqiyatlik qo`shildi",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
            }

            backBtn.setOnClickListener { findNavController().popBackStack() }
            plus.setOnClickListener {
                if (isDelete == null) isDelete = false
                delete(student1, isDelete!!)
                val bundle = Bundle()
                bundle.putBoolean("boolean", false)
                bundle.putSerializable("course_add", group.mentor.course)
                bundle.putSerializable(
                    "student",
                    StudentsClass(-1, "", "", "", "", group, group.mentor)
                )
                findNavController().navigate(R.id.courseAddFragment, bundle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isDelete = false
        size = list.size
        binding.countTxt.text = "Talabalar soni: $size"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isDelete == null) isDelete = false
        delete(student1, isDelete!!)
    }

    fun delete(student: StudentsClass, boolean: Boolean) {
        if (boolean) {
            dbHelper.deleteStudent(student)
            isDelete = false
        }
    }
}