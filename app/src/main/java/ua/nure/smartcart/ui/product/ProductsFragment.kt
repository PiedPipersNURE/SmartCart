package ua.nure.smartcart.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.nure.apiclient.ClientSession
import ua.nure.apiclient.model.core.Product
import ua.nure.smartcart.R

class ProductsFragment : Fragment() {

    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_products, container, false)

        recyclerView = root.findViewById(R.id.recycler_view_products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        refreshButton = root.findViewById(R.id.refresh_button)

        refreshButton.setOnClickListener {
            if (ClientSession.isInSession()){
                val products = ClientSession.getSmartCartClient().productService().getProducts();
                recyclerView.adapter = productsAdapter
                productsAdapter = ProductsAdapter(products)
            }
        }

        val products = listOf(
            Product("dfasfdafd", "fadfasfaf", "Milk", "adssadad", 10, "2021-10-10"),
            Product("dfasfdafd", "fadfasfaf", "Bread", "adssadad", 5, "2021-10-10"),
            Product("dfasfdafd", "fadfasfaf", "Butter", "adssadad", 2, "2021-10-10"),
            Product("dfasfdafd", "fadfasfaf", "Cheese", "adssadad", 1, "2021-10-10"),
        )

        productsAdapter = ProductsAdapter(products)
        recyclerView.adapter = productsAdapter

        return root
    }
}
