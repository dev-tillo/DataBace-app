package uz.pdp.pdpapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.pdpapp.classes.StudentsClass
import uz.pdp.pdpapp.databinding.ItemGroupPagerBinding

class ViewPager3Adapter(
    var boolean: Boolean,
    var list: List<StudentsClass>,
    var listener: OnClickMyListener
) : RecyclerView.Adapter<ViewPager3Adapter.VH>() {

    inner class VH(var binding: ItemGroupPagerBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickMyListener {
        fun onClickDelete(student: StudentsClass, position: Int)
        fun onClickEdit(student: StudentsClass, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGroupPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            courseName.text = "${list[position].name} ${list[position].surname}"
            lastname.text = list[position].lastname

            if (!boolean) {
                show.visibility = View.GONE
            }

            edit.setOnClickListener {
                listener.onClickEdit(list[position], position)
            }

            delete.setOnClickListener {
                listener.onClickDelete(list[position], position)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}