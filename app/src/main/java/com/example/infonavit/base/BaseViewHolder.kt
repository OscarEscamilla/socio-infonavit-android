package com.oscarescamilla.com.base

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T, position: Int)

}