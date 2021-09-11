package uz.pdp.pdpapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.pdp.pdpapp.classes.MentorClass
import uz.pdp.pdpapp.databinding.ItemSpinnerBinding

class SpinAdapter(var list: List<MentorClass>): BaseAdapter() {
    override fun getCount(): Int  = list.size

    override fun getItem(position: Int): MentorClass = list[position]

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