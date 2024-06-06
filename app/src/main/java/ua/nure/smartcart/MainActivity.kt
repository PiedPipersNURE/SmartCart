package ua.nure.smartcart

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.navigation.NavigationView
import ua.nure.apiclient.ClientSession
import ua.nure.apiclient.model.session.GoogleAccountDetails
import ua.nure.smartcart.databinding.ActivityMainBinding
import ua.nure.smartcart.ui.account.AccountFragment

class MainActivity : AppCompatActivity() , AccountFragment.OnAuthChangedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateNavigationHeader(account)
            val email = account.email
            val displayName = account.displayName
            val idToken = account.id
            ClientSession.startSessionWithGoogle(
                GoogleAccountDetails(email, displayName, idToken)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun updateNavigationHeader(
        account: GoogleSignInAccount
    ) {
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val profileIcon = headerView.findViewById<ImageView>(R.id.profile_icon)
        val userEmail = headerView.findViewById<TextView>(R.id.user_email)
        val userName = headerView.findViewById<TextView>(R.id.user_name)

        userEmail.text = account.email ?: "Logged out"
        userName.text = account.displayName ?: "Anonymous user"

        Glide.with(this)
            .load(account.photoUrl)
            .transform(CircleCrop())
            .placeholder(R.drawable.anon_user)
            .error(R.drawable.anon_user)
            .into(profileIcon)
    }

    override fun onChange() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateNavigationHeader(account)
        } else {
            val navView: NavigationView = binding.navView
            val headerView = navView.getHeaderView(0)
            val profileIcon = headerView.findViewById<ImageView>(R.id.profile_icon)
            val userEmail = headerView.findViewById<TextView>(R.id.user_email)
            val userName = headerView.findViewById<TextView>(R.id.user_name)

            userEmail.text = "Logged out"
            userName.text = "Anonymous user"
            profileIcon.setImageResource(R.drawable.anon_user)
        }
    }
}
