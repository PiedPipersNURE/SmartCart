package ua.nure.smartcart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.nure.apiclient.ClientSession
import ua.nure.apiclient.model.core.Cart
import ua.nure.apiclient.model.core.Product
import ua.nure.apiclient.model.session.GoogleAccountDetails
import ua.nure.smartcart.R
import ua.nure.smartcart.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var spinnerCarts: Spinner
    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var productsInCartAdapter: ProductsInCartAdapter
    private lateinit var buttonAddMember: Button
    private lateinit var loginText: TextView
    private lateinit var selectedCartText: TextView
    private lateinit var productsHomeText: TextView

    private var selectedCart: Cart? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        spinnerCarts = root.findViewById(R.id.spinner_carts_home)
        recyclerViewProducts = root.findViewById(R.id.recycler_view_products)
        recyclerViewProducts.layoutManager = LinearLayoutManager(context)
        buttonAddMember = root.findViewById(R.id.button_add_product)
        productsHomeText = root.findViewById(R.id.products_in_cart_text_home)
        loginText = root.findViewById(R.id.login_text_home)
        selectedCartText = root.findViewById(R.id.selected_text_home)


        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            val email = account.email
            val displayName = account.displayName
            val idToken = account.id
            ClientSession.startSessionWithGoogle(
                GoogleAccountDetails(email, displayName, idToken)
            )
        }

        if (!ClientSession.isInSession()) {
            spinnerCarts.visibility = View.GONE
            buttonAddMember.visibility = View.GONE
            recyclerViewProducts.visibility = View.GONE
            productsHomeText.visibility = View.GONE
            selectedCartText.visibility = View.GONE
            loginText.visibility = View.VISIBLE
            return root
        }

        loginText.visibility = View.GONE

        initializeSpinnerAndRecyclerView()

        buttonAddMember.setOnClickListener { showAddProductDialog() }

        return root
    }

    private fun initializeSpinnerAndRecyclerView() {
        spinnerCarts.setSelection(0)
        val carts = ClientSession
            .getSmartCartClient()
            .cartService()
            .getAllCarts()

        val cartNames = carts.map { it.cartName() }
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cartNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarts.adapter = spinnerAdapter

        spinnerCarts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCart = carts.getOrNull(position)
                selectedCart?.let { cart ->
                    val products = ClientSession.getSmartCartClient().productService()
                        .getProductCartId(cart.cartId())
                    updateProducts(products)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        if (carts.isNotEmpty()) {
            selectedCart = carts[0]
            selectedCart?.let { cart ->
                val products = ClientSession.getSmartCartClient().productService()
                    .getProductCartId(cart.cartId())
                updateProducts(products)
            }
        }
    }


    private fun updateProducts(products: MutableList<Product>) {
        productsInCartAdapter = ProductsInCartAdapter(products) { product ->
            showRemoveProductConfirmation(product)
        }
        recyclerViewProducts.adapter = productsInCartAdapter
    }

    private fun showAddProductDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Add product")
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_product, null)
        val inputName = dialogView.findViewById<EditText>(R.id.input_product_name)
        val inputQuantity = dialogView.findViewById<EditText>(R.id.input_quantity)
        builder.setView(dialogView)

        builder.setPositiveButton("Add") { dialog, _ ->
            val productName = inputName.text.toString()
            val quantity = inputQuantity.text.toString().toIntOrNull() ?: 1
            if (productName.isNotEmpty() &&  selectedCart != null) {
                val result = ClientSession.getSmartCartClient().productService()
                    .addProductToCart(selectedCart!!.cartId(), productName, quantity, ClientSession.getUserId())
                if (result.isPresent) {
                    val products = ClientSession.getSmartCartClient().productService()
                        .getProductCartId(selectedCart!!.cartId())
                    updateProducts(products)
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun showRemoveProductConfirmation(product: Product) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Change status")
        builder.setMessage("Are you sure you want change status?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            val result = ClientSession.getSmartCartClient().productService()
                .changeProductStatus(product)
            if (result) {
                val products = ClientSession.getSmartCartClient().productService()
                    .getProductCartId(selectedCart!!.cartId())
                updateProducts(products)
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}