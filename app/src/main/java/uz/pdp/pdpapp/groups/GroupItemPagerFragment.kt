package uz.pdp.pdpapp.groups

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
import uz.pdp.pdpapp.adapters.ViewPager2Adapter
import uz.pdp.pdpapp.adapters.Spin_Mentor_Adapter
import uz.pdp.pdpapp.adapters.Spin_Time_Adapter
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.*

private const val ARG_PARAM1 = "param1"

class GroupItemPagerFragment : Fragment(R.layout.fragment_group_item_pager) {

    private var n: Int? = null
    private var c: Int? = null

    private val binding by viewBinding(FragmentGroupItemPagerBinding::bind)
    lateinit var dbHelper: MyDbHelper
    lateinit var list: ArrayList<Group>
    lateinit var recAdapter2: ViewPager2Adapter
    lateinit var spinnerAdapterMentor: Spin_Mentor_Adapter
    lateinit var spinnerAdapterTime: Spin_Time_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            n = it.getInt(ARG_PARAM1)
            c = it.getInt("id")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = MyDbHelper(requireContext())
        list = ArrayList(dbHelper.getListIsOpenGroup_courseID(n!!, c!!))
        recAdapter2 =
            ViewPager2Adapter(true, dbHelper, list, object : ViewPager2Adapter.OnClickMyListener {
                override fun onclickShow(group: Group, position: Int) {
                    val bundle = Bundle()
                    bundle.putSerializable("group", group)
                    bundle.putInt("size", position)
                    findNavController().navigate(R.id.groupAboutFragment, bundle)
                }

                override fun onClickDelete(mentor: Group, position: Int) {
                    val dialog = AlertDialog.Builder(requireContext())
                    val bindingDialog = ItemDeleteBinding.inflate(layoutInflater)
                    val create = dialog.create()
                    create.setView(bindingDialog.root)
                    bindingDialog.apply {
                        accept.setOnClickListener {
                            dbHelper.deleteGroup(mentor)
                            list.removeAt(position)
                            recAdapter2.notifyItemRemoved(position)
                            recAdapter2.notifyItemChanged(position)
                            create.dismiss()
                        }
                        close.setOnClickListener { create.dismiss() }
                    }
                    create.show()
                }

                override fun onClickEdit(mentor: Group, position: Int) {
                    val dialog = AlertDialog.Builder(requireContext())
                    val binding_dialog = GroupItemBinding.inflate(layoutInflater)
                    val create = dialog.create()
                    create.setView(binding_dialog.root)

                    create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val times = listOf("9:00-11:00", "19:00-21:00")
                    val mentors = dbHelper.getListMentorByID(c!!)
                    var index = -1
                    mentors.forEachIndexed { i, m ->
                        if (mentor.mentor == m) {
                            index = i
                        }
                    }

                    var index_time = -1
                    times.forEachIndexed { i, t ->
                        if (mentor.time == t) {
                            index_time = i
                        }
                    }

                    spinnerAdapterMentor = Spin_Mentor_Adapter(mentors)
                    spinnerAdapterTime = Spin_Time_Adapter(times)
                    binding_dialog.apply {

                        spinMentorEdit.adapter = spinnerAdapterMentor
                        spinMentorEdit.setSelection(index)
                        spinGroupTime.adapter = spinnerAdapterTime
                        spinGroupTime.setSelection(index_time)
                        lastname.setText(mentor.name)

                        accept.setOnClickListener {
                            val mentor_name = mentors[spinMentorEdit.selectedItemPosition].name
                            val grup_time = times[spinGroupTime.selectedItemPosition]
                            if (lastname.text.isNotEmpty() && mentor_name.isNotEmpty() && grup_time.isNotEmpty()) {
                                val course = Group(
                                    mentor.id,
                                    lastname.text.toString(),
                                    mentor.isOpen,
                                    mentor.date,
                                    grup_time,
                                    mentor.mentor
                                )
                                dbHelper.editGroup(course)
                                list[position] = course
                                recAdapter2.notifyItemChanged(position)
                            }
                            create.dismiss()
                        }
                        close.setOnClickListener { create.dismiss() }
                    }
                    create.show()
                }

            })
        binding.apply {
            recycle.adapter = recAdapter2
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

    companion object {
        fun newInstance(n: Int, c: Int) =
            GroupItemPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, n)
                    putInt("id", c)
                }
            }
    }
}