package ua.nure.smartcart.ui.cartgallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ua.nure.smartcart.R
import ua.nure.smartcart.databinding.FragmentGalleryBinding

class CartGalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartsAdapter: CartsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private val carts = mutableListOf<Cart>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this)[CartGalleryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.recycler_view_carts)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fab = root.findViewById(R.id.fab_add_cart)
        fab.setOnClickListener { showAddCartDialog() }

        cartsAdapter = CartsAdapter(carts) { cart -> showDeleteCartDialog(cart) }
        recyclerView.adapter = cartsAdapter

        // Example data
        carts.add(Cart("Cart 1"))
        carts.add(Cart("Cart 2"))

        cartsAdapter.notifyDataSetChanged()

        return root
    }

    private fun showAddCartDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Add New Cart")
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_cart, null)
        val input = dialogView.findViewById<EditText>(R.id.input_cart_name)
        builder.setView(dialogView)

        builder.setPositiveButton("Add") { dialog, _ ->
            val cartName = input.text.toString()
            if (cartName.isNotEmpty()) {
                carts.add(Cart(cartName))
                cartsAdapter.notifyItemInserted(carts.size - 1)
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun showDeleteCartDialog(cart: Cart) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Delete Cart")
        builder.setMessage("Are you sure you want to delete this cart?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            val position = carts.indexOf(cart)
            if (position != -1) {
                carts.removeAt(position)
                cartsAdapter.notifyItemRemoved(position)
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
