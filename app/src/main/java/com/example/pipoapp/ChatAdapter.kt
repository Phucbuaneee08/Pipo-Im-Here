package com.example.pipoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pipoapp.databinding.ItemContainerReceiveMessageBinding
import com.example.pipoapp.databinding.ItemContainerSentMessageBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listAdapter = mutableListOf<String>()
    private val typeSend = 0;
    private val typereceive = 1;

    class ViewHoderSend(val binding: ItemContainerSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHoderReceive(val binding: ItemContainerReceiveMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun getItemViewType(position: Int): Int {
        return when (position % 2) {
            0 -> typeSend
            else -> typereceive
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bindingSend = ItemContainerSentMessageBinding.inflate(layoutInflater, parent, false)
        val bindingReceive =
            ItemContainerReceiveMessageBinding.inflate(layoutInflater, parent, false)
        return when (viewType) {
            typeSend -> ViewHoderSend(bindingSend)
            else -> ViewHoderReceive(bindingReceive)
        }
    }

    override fun getItemCount(): Int = listAdapter.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHoderSend) {
            holder.binding.tvMess.text = listAdapter[position]
        }
        if (holder is ViewHoderReceive) {
            holder.binding.tvMess.text = listAdapter[position]
        }
    }

    fun setAdapter(message: String) {
        listAdapter.add(message)
        notifyDataSetChanged()
    }

}