package com.example.demowork1.base.recyclerView

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH:RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    fun convert(viewHolder: VH) {

    }
}