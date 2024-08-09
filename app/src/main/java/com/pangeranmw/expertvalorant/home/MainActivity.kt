package com.pangeranmw.expertvalorant.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.pangeranmw.expertvalorant.R
import com.pangeranmw.expertvalorant.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.navView
        drawerLayout = binding.main

        binding.btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        val dynamicNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as DynamicNavHostFragment
        navController = dynamicNavHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navView.setupWithNavController(navController)
        setNavViewListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setNavViewListener(
        onNavigate:(NavController, Int) -> Unit? = {_,_ ->},
        onChecked: (NavigationView) -> Unit? = {_->}
    ){
        val isHome = navController.currentDestination?.id == R.id.nav_home
        val isFavorite = navController.currentDestination?.id == R.id.navigation_favorite_graph
        val isDetail = navController.currentDestination?.id == R.id.nav_detail

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_favorite_graph -> {
                    if(!isDetail){
                        if(isHome) loadFavoritesModule(R.id.action_nav_home_to_featureFavorite)
                    }else{
                        // if detail from navigation_favorite_graph
                        if(navController.previousBackStackEntry?.destination?.label=="Favorite")
                            navController.popBackStack()
                        // else from navigation_main
                        else loadFavoritesModule(R.id.action_nav_detail_to_featureFavorite)
                    }
                }
                R.id.nav_home -> {
                    if(!isHome){
                        navController.popBackStack(R.id.nav_home,false)
                    }
                }
                else -> {
                    onNavigate(navController,menuItem.itemId)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
        onChecked(navView)
        if(isHome) navView.setCheckedItem(R.id.nav_home)
        if(isFavorite) navView.setCheckedItem(R.id.navigation_favorite_graph)
    }

    private fun loadFavoritesModule(action: Int) {
        val installMonitor = DynamicInstallMonitor()
        navController.navigate(
            action,
            null,
            null,
            DynamicExtras(installMonitor)
        )
        if (installMonitor.isInstallRequired) {
            installMonitor.status.observe(this, object : Observer<SplitInstallSessionState> {
                override fun onChanged(value: SplitInstallSessionState) {
                    when (value.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            makeToast("Module Opened")
                            navController.navigate(action)
                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                            makeToast("REQUIRES_USER_CONFIRMATION to load module: $value")
                        }
                        SplitInstallSessionStatus.FAILED -> {
                            makeToast("Failed to load module: $value")
                        }
                        SplitInstallSessionStatus.CANCELED -> {
                            makeToast("CANCELED")
                        }

                        SplitInstallSessionStatus.CANCELING -> {
                            makeToast("CANCELING")
                        }

                        SplitInstallSessionStatus.DOWNLOADED -> {
                            makeToast("DOWNLOADED")
                        }

                        SplitInstallSessionStatus.DOWNLOADING -> {
                            makeToast("DOWNLOADING")
                        }

                        SplitInstallSessionStatus.INSTALLING -> {
                            makeToast("INSTALLING")
                        }

                        SplitInstallSessionStatus.PENDING -> {
                            makeToast("PENDING")
                        }

                        SplitInstallSessionStatus.UNKNOWN -> {
                            makeToast("UNKNOWN")
                        }
                    }

                    if (value.hasTerminalStatus()) {
                        installMonitor.status.removeObserver(this)
                    }
                }
            })
        }
    }
    private fun makeToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}