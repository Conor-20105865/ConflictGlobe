package com.example.conflictglobe

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//connects conflict records to the recycler view list (show full conflict list on main page)
// Adapter to manage and display a list of Conflict items in a RecyclerView
class ConflictAdapter(
    private var items: List<Conflict>,
    private val onClick: (Conflict) -> Unit, //when clicky clicky do action
) : RecyclerView.Adapter<ConflictAdapter.Holder>() {

    //references to ui views for each list item
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val meta: TextView = view.findViewById(R.id.meta)
        val markImage: ImageView = view.findViewById(R.id.markImage)
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

        val imageUri = conflict.imageUri
        if (imageUri != null) {
            try {
                holder.markImage.setImageURI(Uri.parse(imageUri))
            } catch (_: Exception) {
                holder.markImage.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        } else {
            holder.markImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }

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