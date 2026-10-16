package com.example.conflictglobe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter to manage and display a list of Conflict items in a RecyclerView
class ConflictAdapter(
    private var items: List<Conflict>,
    private val onClick: (Conflict) -> Unit, //when clicky clicky do action
) : RecyclerView.Adapter<ConflictAdapter.Holder>() {

    //references to ui views for each list item
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val meta: TextView = view.findViewById(R.id.meta)
    }

    //loads item layout fiel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conflict, parent, false)

        return Holder(view)
    }


    override fun getItemCount(): Int = items.size

    //put data in view for each row
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val conflict = items[position]

        //show the info on the conflict
        holder.name.text = conflict.name
        holder.meta.text =
            "${conflict.region} • ${conflict.status} • ${conflict.severity}"

        //clicky clicky checker
        holder.itemView.setOnClickListener {
            onClick(conflict)
        }
    }

    //updates the list with shiny new data!
    @SuppressLint("NotifyDataSetChanged")
    fun update(newItems: List<Conflict>) {
        items = newItems
        notifyDataSetChanged()
    }
}