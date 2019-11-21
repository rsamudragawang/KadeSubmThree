package com.ganargatul.submthree.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.submthree.R
import com.ganargatul.submthree.model.SearchEventsItems


class SearchEventAdapter(private  val context: Context, private val Items: List<SearchEventsItems>, private val listener: (SearchEventsItems) -> Unit): RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.title_items)

        fun bindItem(items: SearchEventsItems, listener: (SearchEventsItems) -> Unit){

                name.text = items.strEvent
//            items.image?.let { Picasso.get().load(it).into(img) }
                itemView.setOnClickListener { listener(items) }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.next_event_item,parent,false))
    }

    override fun getItemCount(): Int = Items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(Items[position],listener)
    }
}