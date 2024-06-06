package ua.nure.smartcart.ui.cartselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nure.smartcart.R

data class Member(val memberName: String)

class MembersAdapter(
    private val members: MutableList<Member>,
    private val onRemoveClick: (Member) -> Unit
) : RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

    class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val memberName: TextView = view.findViewById(R.id.member_name)
        val removeButton: ImageButton = view.findViewById(R.id.button_remove_member)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.memberName.text = member.memberName
        holder.removeButton.setOnClickListener {
            onRemoveClick(member)
        }
    }

    override fun getItemCount() = members.size
}
