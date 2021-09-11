package uz.pdp.pdpapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.databinding.ItemCourseListBinding

class ViewAdapter(var list: List<GroupClass>, var listener: OnClickMyListener) :
    RecyclerView.Adapter<ViewAdapter.VH>() {

    inner class VH(var binding: ItemCourseListBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickMyListener {
        fun onClick(course: GroupClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemCourseListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            courseName.text = list[position].name

            root.setOnClickListener {
                listener.onClick(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size
}