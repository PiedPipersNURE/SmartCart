package ua.nure.smartcart.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nure.apiclient.model.core.Product
import ua.nure.smartcart.R

class ProductsInCartAdapter(
    private val products: MutableList<Product>,
    private val onRemoveClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductsInCartAdapter.ProductInCartViewHolder>() {

    class ProductInCartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name_home)
        val quantity: TextView = view.findViewById(R.id.product_quantity_home)
        val changeButton: ImageView = view.findViewById(R.id.product_status_button)
        val status: TextView = view.findViewById(R.id.product_status_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_home, parent, false)
        return ProductInCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductInCartViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.productName()
        holder.quantity.text = product.quantity().toString()
        if (product.isBought){
            holder.status.text = "Bought"
            holder.changeButton.setImageResource(R.drawable.bought_icon)
        }else{
            holder.status.text = "In cart"
            holder.changeButton.setImageResource(R.drawable.in_cart_icon)
        }
        holder.changeButton.setOnClickListener {
            onRemoveClick(product)
        }
    }

    override fun getItemCount() = products.size
}
