package ua.nure.smartcart.ui.cartgallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nure.smartcart.R

data class Cart(val cartName: String)

class CartsAdapter(private val carts: MutableList<Cart>, private val onDeleteClick: (Cart) -> Unit) :
    RecyclerView.Adapter<CartsAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cartName: TextView = view.findViewById(R.id.cart_name)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = carts[position]
        holder.cartName.text = cart.cartName
        holder.deleteButton.setOnClickListener {
            onDeleteClick(cart)
        }
    }

    override fun getItemCount() = carts.size
}
