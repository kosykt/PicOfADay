package ru.konstantin.material.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.material.databinding.FragmentMainRecyclerItemBinding
import ru.konstantin.material.model.Item
import ru.konstantin.material.ui.fragments.OnItemViewClickListener

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var data: List<Item> = arrayListOf()

    private var onItemViewClickListener: OnItemViewClickListener? = null

    fun setData(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = FragmentMainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: FragmentMainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Item){
            binding.root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(data)
            }
            binding.apply {
                itemId.text = data.id.toString()
                itemTitle.text = data.title
            }

        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: OnItemViewClickListener){
        this.onItemViewClickListener = onItemViewClickListener
    }
}