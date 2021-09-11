package uz.pdp.pdpapp.kurslar

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.pdpapp.R
import uz.pdp.pdpapp.adapters.ViewAdapter
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.FragmentKursListBinding
import uz.pdp.pdpapp.databinding.ItemGroupBinding

class Kurs_List_Fragment : Fragment(R.layout.fragment_kurs_list) {

    private val binding by viewBinding(FragmentKursListBinding::bind)
    lateinit var dbHelper: MyDbHelper
    lateinit var recAdapter: ViewAdapter
    lateinit var list: ArrayList<GroupClass>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = ArrayList(dbHelper.getListCourse())
        recAdapter = ViewAdapter(list, object : ViewAdapter.OnClickMyListener{
            override fun onClick(course: GroupClass) {
                val bundle = Bundle()
                bundle.putSerializable("course", course)
                findNavController().navigate(R.id.courseAboutFragment, bundle)
            }
        })

        binding.apply {
            recycle.adapter = recAdapter

            plus.setOnClickListener {
                val dialog = AlertDialog.Builder(requireContext())
                val binding_dialog = ItemGroupBinding.inflate(layoutInflater)
                val create = dialog.create() as AlertDialog
                create.setView(binding_dialog.root)

                create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                binding_dialog.apply {
                    accept.setOnClickListener {
                        if (courseName.text.isNotEmpty() && courseAbout.text.isNotEmpty()){
                            val course = GroupClass(name = courseName.text.toString(), description = courseAbout.text.toString())
                            dbHelper.addCourse(course)
                            val list1 = ArrayList<GroupClass>(dbHelper.getListCourse())
                            list.add(list1[list1.size-1])
                            recAdapter.notifyItemInserted(list.size)
                        }
                        create.dismiss()
                    }

                    close.setOnClickListener { create.dismiss() }
                }
                create.show()
            }

            backBtn.setOnClickListener { findNavController().popBackStack() }
        }
    }
}