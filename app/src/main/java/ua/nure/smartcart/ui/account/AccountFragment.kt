package ua.nure.smartcart.ui.account

import android.annotation.SuppressLint
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
import ua.nure.apiclient.model.session.GoogleAccountDetails
import ua.nure.apiclient.model.session.LoginDetails
import ua.nure.smartcart.R
import ua.nure.smartcart.R.layout
import ua.nure.smartcart.ui.registration.RegistrationActivity

class AccountFragment : Fragment() {

    private var logoutListener: OnAuthChangedListener? = null

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginNameTextView: TextView
    private lateinit var userProfileImageView: ImageView
    private lateinit var loginButton : Button
    private lateinit var logoutButton : Button
    private lateinit var loginPasswordTextView : TextView
    private lateinit var loginEditText : TextView
    private lateinit var orTextView : TextView
    private lateinit var orTextView2 : TextView
    private lateinit var googleLoginButton : ImageView
    private lateinit var registerButton : Button
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

        loginButton = root.findViewById(R.id.login_button)
        logoutButton = root.findViewById(R.id.logout_button)
        loginNameTextView = root.findViewById(R.id.login_name)
        userProfileImageView = root.findViewById(R.id.user_profile_icon)
        loginPasswordTextView = root.findViewById(R.id.login_password)
        orTextView = root.findViewById(R.id.or_text1)
        orTextView2 = root.findViewById(R.id.or_text2)
        googleLoginButton = root.findViewById(R.id.login_google_button)
        registerButton = root.findViewById(R.id.go_register_button)
        loginEditText = root.findViewById(R.id.login_text)


        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (account != null) {
            updateUI(account)
            showLoginOption(false)
        } else {
            loginNameTextView.text = "Not signed in"
            showLoginOption(true)
        }

        googleLoginButton.setOnClickListener {
            signInWithGoogle()
        }

        logoutButton.setOnClickListener {
            signOut()
        }

        loginButton.setOnClickListener{
            signIn()
        }

        registerButton.setOnClickListener{
            register()
        }

        return root
    }

    private fun register() {
        val intent = Intent(activity, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        val details = LoginDetails(
            loginEditText.text.toString(),
            loginPasswordTextView.text.toString());
        ClientSession.startSession(details)
    }

    private fun showLoginOption(b: Boolean) {
        if (b) {
            loginButton.visibility = View.VISIBLE
            loginPasswordTextView.visibility = View.VISIBLE
            orTextView.visibility = View.VISIBLE
            orTextView2.visibility = View.VISIBLE
            googleLoginButton.visibility = View.VISIBLE
            registerButton.visibility = View.VISIBLE
            loginEditText.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        } else {
            loginButton.visibility = View.GONE
            loginPasswordTextView.visibility = View.GONE
            loginEditText.visibility = View.GONE
            orTextView.visibility = View.GONE
            orTextView2.visibility = View.GONE
            googleLoginButton.visibility = View.GONE
            registerButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
            loginNameTextView.text = "You are anonymous user"
            userProfileImageView.setImageResource(R.drawable.anon_user)
            logoutListener?.onChange()
            showLoginOption(true)
            ClientSession.endSession()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                updateUI(account)
                logoutListener?.onChange()
                val accountDetails = getAccountDetails(account)
                ClientSession.startSessionWithGoogle(accountDetails)
                if (ClientSession.isInSession()) {
                    Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
                    showLoginOption(false)
                }else{
                    Toast.makeText(requireContext(), "Not logged in", Toast.LENGTH_SHORT).show()
                    showLoginOption(true)
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Something went wrong: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAccountDetails(account: GoogleSignInAccount?): GoogleAccountDetails? {
        val username = account?.displayName
        val email = account?.email
        val id = account?.id

        return GoogleAccountDetails(
            email,
            username,
            id
        )
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


