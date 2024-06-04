package ua.nure.smartcart.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import ua.nure.smartcart.R
import ua.nure.smartcart.R.layout

class AccountFragment : Fragment() {

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(layout.fragment_account, container, false)

        val loginButton = root.findViewById<Button>(R.id.login_button)
        val logoutButton = root.findViewById<Button>(R.id.logout_button)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())

        loginButton.setOnClickListener {
            signIn()
        }

        logoutButton.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)
                Toast.makeText(requireContext(), "Ok!", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}