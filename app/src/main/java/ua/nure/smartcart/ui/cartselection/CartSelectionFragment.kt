package ua.nure.smartcart.ui.cartselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.nure.smartcart.R
import ua.nure.smartcart.databinding.FragmentSlideshowBinding

data class Cart(val cartName: String, val members: MutableList<Member>)

class CartSelectionFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    private lateinit var spinnerCarts: Spinner
    private lateinit var recyclerViewMembers: RecyclerView
    private lateinit var membersAdapter: MembersAdapter
    private lateinit var buttonAddMember: Button

    private val carts = mutableListOf(
        Cart("Cart 1", mutableListOf(Member("Member 1.1"), Member("Member 1.2"))),
        Cart("Cart 2", mutableListOf(Member("Member 2.1"), Member("Member 2.2"), Member("Member 2.3"))),
        Cart("Cart 3", mutableListOf(Member("Member 3.1"), Member("Member 3.2")))
    )

    private var selectedCart: Cart? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(CartSelectionViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        spinnerCarts = root.findViewById(R.id.spinner_carts)
        recyclerViewMembers = root.findViewById(R.id.recycler_view_members)
        recyclerViewMembers.layoutManager = LinearLayoutManager(context)
        buttonAddMember = root.findViewById(R.id.button_add_member)

        val cartNames = carts.map { it.cartName }
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cartNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarts.adapter = spinnerAdapter

        spinnerCarts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedCart = carts[position]
                updateMembers(selectedCart!!.members)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        buttonAddMember.setOnClickListener { showAddMemberDialog() }

        // Initialize with the first cart's members
        if (carts.isNotEmpty()) {
            selectedCart = carts[0]
            updateMembers(selectedCart!!.members)
        }

        return root
    }

    private fun updateMembers(members: MutableList<Member>) {
        membersAdapter = MembersAdapter(members) { member ->
            showRemoveMemberConfirmation(member)
        }
                recyclerViewMembers.adapter = membersAdapter
    }

    private fun showAddMemberDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Add Member")
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_member, null)
        val input = dialogView.findViewById<EditText>(R.id.input_member_name)
        builder.setView(dialogView)

        builder.setPositiveButton("Add") { dialog, _ ->
            val memberName = input.text.toString()
            if (memberName.isNotEmpty()) {
                selectedCart?.members?.add(Member(memberName))
                membersAdapter.notifyDataSetChanged()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun showRemoveMemberConfirmation(member: Member) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Remove Member")
        builder.setMessage("Are you sure you want to remove this member?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            selectedCart?.members?.remove(member)
            membersAdapter.notifyDataSetChanged()
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

