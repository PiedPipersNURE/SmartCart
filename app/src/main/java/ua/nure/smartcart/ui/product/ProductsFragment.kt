package ua.nure.smartcart.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.nure.smartcart.R

class ProductsFragment : Fragment() {

    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_products, container, false)

        recyclerView = root.findViewById(R.id.recycler_view_products)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Example data
        val products = listOf(
            Product("Banana", 1, true),
            Product("Apple", 20, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Apple", 2, false),
            Product("Pineapple", 5, true)
        )

        productsAdapter = ProductsAdapter(products)
        recyclerView.adapter = productsAdapter

        return root
    }
}
