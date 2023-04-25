package com.example.knihovna.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knihovna.ItemDetailActivity
import com.example.knihovna.R
import com.example.knihovna.data.InfoBookAdapter

//to show in RecycledView
class AdapterBooks(private var items: MutableList<InfoBookAdapter>, private val onClickCallback: (Long) -> Unit, type:Int) : RecyclerView.Adapter<AdapterBooks.ItemHolder> () {
    var type:Int = type
    inner class ItemHolder(view: View, type:Int) : RecyclerView.ViewHolder(view), View.OnClickListener{
        internal val bookName = view.findViewById<TextView>(R.id.item_fav_name)
        internal val authorName = view.findViewById<TextView>(R.id.item_fav_author)
        internal val isFav = view.findViewById<ImageView>(R.id.item_fav_icon)


        init {
            view.isClickable = true
            if(type == 0) view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //after click on item it will open detail
            val intent = Intent(p0?.context, ItemDetailActivity::class.java)
            intent.putExtra("book_name", bookName.text)
            intent.putExtra("author_name", authorName.text)
            p0?.context?.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemHolder(item, type)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ItemHolder, pos: Int) {

        holder.bookName.text = items[pos].book_name
        holder.authorName.text = items[pos].author_name
        if(items[pos].is_fav) holder.isFav.setImageDrawable(holder.itemView.resources.getDrawable(R.drawable.heart_icon_full))
        else holder.isFav.setImageDrawable(holder.itemView.resources.getDrawable(R.drawable.heart_icon))
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<InfoBookAdapter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}