package com.example.infonavit.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infonavit.R
import com.example.infonavit.data.models.benevits.BenevitItem
import com.example.infonavit.databinding.BenevitItemLockedBinding
import com.example.infonavit.databinding.BenevitItemUnlockedBinding
import com.oscarescamilla.com.base.BaseViewHolder

class BenevitsAdapter(val context: Context, itemsBenevit: ArrayList<BenevitItem>, val walletName: String):
        RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val VIEW_LOCKED = 1;
    private val VIEW_UNLOCKED = 2;

    var itemsBenevit = arrayListOf<BenevitItem>()


    init {
        this.itemsBenevit = itemsBenevit.filter { it.wallet.name == walletName } as ArrayList<BenevitItem>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType){
            VIEW_LOCKED -> {
                ViewHolderLocked(LayoutInflater.from(context).inflate(R.layout.benevit_item_locked, parent, false))
            }
            VIEW_UNLOCKED -> {
                ViewHolderUnlocked(LayoutInflater.from(context).inflate(R.layout.benevit_item_unlocked, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }


    override fun getItemCount(): Int {
        return itemsBenevit.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = itemsBenevit[position]
        when (holder) {
            is ViewHolderUnlocked -> holder.bind(element, position)
            is ViewHolderLocked -> holder.bind(element, position)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {

        val comparable = itemsBenevit[position].view_type
        return when (comparable) {
            "unlocked" -> VIEW_UNLOCKED
            "locked" -> VIEW_LOCKED
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }

    }


    inner class ViewHolderUnlocked(itemView: View): BaseViewHolder<BenevitItem>(itemView) {

        val binding = BenevitItemUnlockedBinding.bind(itemView)

        override fun bind(item: BenevitItem, position: Int) {
            binding.txtDescription.text = item.description
            binding.txtTerritory.text = item.territories[0].name
            Glide.with(context)
                .load(item.ally.mini_logo_full_path)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.imgBrand)
        }

    }


    inner class ViewHolderLocked(itemView: View): BaseViewHolder<BenevitItem>(itemView) {

        val binding = BenevitItemLockedBinding.bind(itemView)

        override fun bind(item: BenevitItem, position: Int) {
            Glide.with(context)
                .load(item.vector_full_path)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.imgProduct)
        }

    }



}