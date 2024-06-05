package ua.nure.smartcart.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import ua.nure.apiclient.ClientSession
import ua.nure.apiclient.model.GoogleAccountDetails
import ua.nure.smartcart.R
import ua.nure.smartcart.R.layout

class AccountFragment : Fragment() {

    private var logoutListener: OnAuthChangedListener? = null

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginNameTextView: TextView
    private lateinit var userProfileImageView: ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAuthChangedListener) {
            logoutListener = context
        } else {
            throw RuntimeException("$context must implement OnLogoutListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(layout.fragment_account, container, false)

        val loginButton = root.findViewById<Button>(R.id.login_button)
        val logoutButton = root.findViewById<Button>(R.id.logout_button)
        loginNameTextView = root.findViewById(R.id.login_name)
        userProfileImageView = root.findViewById(R.id.user_profile_icon)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (account != null) {
            updateUI(account)
        } else {
            loginNameTextView.text = "Not signed in"
        }

        loginButton.setOnClickListener {
            signIn()
        }

        logoutButton.setOnClickListener {
            signOut()
        }

        return root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
            loginNameTextView.text = "You are anonymous user"
            userProfileImageView.setImageResource(R.drawable.anon_user)
            logoutListener?.onChange()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(requireContext(), "Ok!", Toast.LENGTH_SHORT).show()
                updateUI(account)
                logoutListener?.onChange()
                val accountDetails = getAccountDetails(account)
                val session = ClientSession.getInstance(accountDetails)
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Something went wrong: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAccountDetails(account: GoogleSignInAccount?): GoogleAccountDetails? {
        val username = account?.displayName
        val email = account?.email
        val id = account?.id

        return GoogleAccountDetails(email, username, id)
    }

    private fun updateUI(account: GoogleSignInAccount) {
        loginNameTextView.text = account.displayName ?: "You are anonymous user"
        account.photoUrl?.let { uri ->
            Glide.with(this)
                .load(uri)
                .transform(CircleCrop())
                .placeholder(R.drawable.anon_user)
                .error(R.drawable.anon_user)
                .into(userProfileImageView)
        } ?: run {
            userProfileImageView.setImageResource(R.drawable.anon_user)
        }
    }

    interface OnAuthChangedListener {
        fun onChange()
    }

}


