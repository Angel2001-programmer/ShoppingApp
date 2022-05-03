package com.angel.test

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.adapters.BasketAdapter
import com.angel.test.adapters.SwipeGesture
import com.angel.test.databinding.ActivityMainBinding
import com.angel.test.fragments.MoreDetailsFragment
import com.angel.test.ui.basket.BasketFragment
import com.angel.test.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var homeFragment = HomeFragment()
    val moreDetailsFragment = MoreDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_basket
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, homeFragment).commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Get data ready to be transferred to another fragment/activity.
    override fun passProductData(
        id: Int,
        image: String,
        name: String,
        desc: String,
        price: String
    ) {
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("uri", image)
        bundle.putString("title", name)
        bundle.putString("description", desc)
        bundle.putString("price", price)

        val transaction = this.supportFragmentManager.beginTransaction()
        moreDetailsFragment.arguments = bundle

        transaction.replace(R.id.nav_host_fragment_content_main, moreDetailsFragment)
            .addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }

    // Get data ready to be transferred to another fragment/activity.
    override fun passBasketInfo(id: Int, image: String, name: String, price: String) {
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("uri", image)
        bundle.putString("title", name)
        bundle.putString("price", price)
        val transaction = this.supportFragmentManager.beginTransaction()
        val slideFragment = BasketFragment()
        slideFragment.arguments = bundle

        transaction.replace(R.id.nav_host_fragment_content_main, slideFragment)
        transaction.commit()
    }
}