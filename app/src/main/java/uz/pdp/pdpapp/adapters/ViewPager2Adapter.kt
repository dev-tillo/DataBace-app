package uz.pdp.pdpapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.database.MyDbHelper
import uz.pdp.pdpapp.databinding.ItemGroupPagerBinding

class ViewPager2Adapter(var boolean: Boolean, var dbHelper: MyDbHelper, var list: List<Group>, var listener: OnClickMyListener):RecyclerView.Adapter<ViewPager2Adapter.VH>() {

    inner class VH(var binding: ItemGroupPagerBinding): RecyclerView.ViewHolder(binding.root)

    interface OnClickMyListener {
        fun onclickShow(group: Group, position: Int)
        fun onClickDelete(mentor: Group, position: Int)
        fun onClickEdit(mentor: Group, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGroupPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            courseName.text = list[position].name
            lastname.text = "Talabalar soni: ${dbHelper.getListStudentByID(list[position].id).size}"

            if (!boolean) {
                show.visibility = View.GONE
            }

            edit.setOnClickListener {
                listener.onClickEdit(list[position], position)
            }

            delete.setOnClickListener {
                listener.onClickDelete(list[position], position)
            }

            show.setOnClickListener {
                listener.onclickShow(list[position], dbHelper.getListStudentByID(list[position].id).size)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}