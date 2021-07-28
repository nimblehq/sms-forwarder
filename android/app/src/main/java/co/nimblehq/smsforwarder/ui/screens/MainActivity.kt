package co.nimblehq.smsforwarder.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.ActivityMainBinding
import co.nimblehq.smsforwarder.ui.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        { inflater -> ActivityMainBinding.inflate(inflater) }

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun bindViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.tbMain)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fcvMain) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        val drawerLayout: DrawerLayout? = findViewById(R.id.dlMain)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.historyFragment),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        setupNavigationMenu(navController)
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nvMain)
        sideNavView?.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fcvMain))
            || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fcvMain).navigateUp(appBarConfiguration)
    }
}
