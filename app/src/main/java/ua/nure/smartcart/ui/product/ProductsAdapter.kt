package ua.nure.smartcart.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nure.apiclient.model.core.Product
import ua.nure.smartcart.R

data class Product(
    val productName: String,
    val productQuantity: Int,
    val isBought: Boolean
)

class ProductsAdapter(private val products: MutableList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val productQuantity: TextView = view.findViewById(R.id.product_quantity)
        val productStatus: TextView = view.findViewById(R.id.product_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.productName()
        holder.productQuantity.text = product.quantity().toString()
        holder.productStatus.text = product.estimatedDate()
    }

    override fun getItemCount() = products.size
}
