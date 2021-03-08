package com.example.infonavit.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infonavit.R
import com.example.infonavit.data.models.benevits.BenevitItem
import com.example.infonavit.data.models.wallets.WalletResponseItem
import com.example.infonavit.databinding.WalletRowBinding
import com.oscarescamilla.com.base.BaseViewHolder

class WalletsAdapter(val context: Context, val itemsWallet: ArrayList<WalletResponseItem>, val benevitItems: ArrayList<BenevitItem>):
        RecyclerView.Adapter<BaseViewHolder<*>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        //val itemBinding = WalletRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(LayoutInflater.from(context).inflate( R.layout.wallet_row, parent, false))
        //return  ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return itemsWallet.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ViewHolder -> holder.bind(itemsWallet[position], position)
        }
    }



    inner class ViewHolder(private val itemView: View): BaseViewHolder<WalletResponseItem>(itemView) {

        val binding = WalletRowBinding.bind(itemView)

        override fun bind(item: WalletResponseItem, position: Int) {
            binding.txtNameWallet.text = item.name

            binding.recyclerBenevits.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerBenevits.adapter = BenevitsAdapter(context, benevitItems, item.name)
        }

    }

}