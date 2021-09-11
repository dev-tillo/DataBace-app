package uz.pdp.pdpapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.databinding.ItemSpinnerBinding

class Spin_Adapter_Group(var list: List<Group>): BaseAdapter() {
    override fun getCount(): Int  = list.size

    override fun getItem(position: Int): Group = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding: ItemSpinnerBinding
        if (convertView == null) {
            binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            binding = ItemSpinnerBinding.bind(convertView)
        }

        binding.txt.text = list[position].name

        return  binding.root
    }
}