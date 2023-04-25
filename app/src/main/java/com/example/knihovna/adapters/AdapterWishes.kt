package com.example.knihovna.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knihovna.R
import com.example.knihovna.data.WishAdapter
import com.example.knihovna.viewModels.WishlistActivityViewModel

class AdapterWishes (private var items: MutableList<WishAdapter>, private val onClickCallback: (Long) -> Unit, type:Int, private val viewModel: WishlistActivityViewModel) : RecyclerView.Adapter<AdapterWishes.ItemHolder> () {

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
            val context = p0?.context
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Smazání knihy")
            alertDialog.setMessage("Chcete knihu smazat ze seznamu přání?")
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ano") { dialog, which ->

                viewModel.deleteWish(bookName.text as String, authorName.text as String)

            }
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ne") { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemHolder(item, type)
    }

    override fun onBindViewHolder(holder: ItemHolder, pos: Int) {
        holder.bookName.text = items[pos].book_name
        holder.authorName.text = items[pos].author_name
        holder.isFav.visibility = View.GONE

    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<WishAdapter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}