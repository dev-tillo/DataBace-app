package uz.pdp.pdpapp.fragments.mentors

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.ViewPager1Adapter
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.classes.MentorClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.DialogMentorBinding
import uz.pdp.pdpapp.databinding.FragmentMentorenterBinding

class Mentor_Fragment : Fragment(R.layout.fragment_mentorenter) {

    private val binding by viewBinding(FragmentMentorenterBinding::bind)
    lateinit var dbHelper: MyDbHelper
    lateinit var list: ArrayList<MentorClass>
    lateinit var course: GroupClass
    lateinit var recAdapter1: ViewPager1Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_mentor") as GroupClass
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = ArrayList(dbHelper.getListMentorByID(course.id))

        recAdapter1 = ViewPager1Adapter(false, list, object : ViewPager1Adapter.OnClickMyListener{
            override fun onClickDelete(mentor: MentorClass, position: Int) {
                dbHelper.deleteMentor(mentor)
                list.removeAt(position)
                recAdapter1.notifyItemRemoved(position)
            }

            override fun onClickEdit(mentor: MentorClass, position: Int) {
                val dialog = AlertDialog.Builder(requireContext())
                val binding_dialog = DialogMentorBinding.inflate(layoutInflater)
                val create = dialog.create()
                create.setView(binding_dialog.root)

                create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                binding_dialog.apply {

                    lastname.setText(mentor.surname)
                    nameEdit.setText(mentor.name)
                    name.setText(mentor.lastname)

                    accept.setOnClickListener {
                        if (lastname.text.isNotEmpty() && nameEdit.text.isNotEmpty() && name.text.isNotEmpty()){
                            val course = MentorClass(mentor.id, nameEdit.text.toString(), lastname.text.toString(), name.text.toString(), mentor.course)
                            dbHelper.editMentor(course)
                            list[position] = course
                            recAdapter1.notifyItemChanged(position)
                        }
                        create.dismiss()
                    }
                    close.setOnClickListener { create.dismiss() }
                }
                create.show()
            }

        })
        binding.apply {
            plus.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("course_add_mentor", course)
                findNavController().navigate(R.id.mentorAboutFragment, bundle)
            }

            backBtn.setOnClickListener { findNavController().popBackStack() }

            recycle.adapter = recAdapter1
            recycle.setDivider(R.drawable.bottom_line)
        }
    }

    fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
        val divider = DividerItemDecoration(
            this.context,
            DividerItemDecoration.VERTICAL
        )
        val drawable = ContextCompat.getDrawable(
            this.context,
            drawableRes
        )
        drawable?.let {
            divider.setDrawable(it)
            addItemDecoration(divider)
        }
    }
}