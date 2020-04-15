package com.andromeda.swvlclone

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null) {
            val __manager__ = supportFragmentManager
            val _transaction__ = __manager__.beginTransaction()
            _transaction__.replace(
                R.id.container,
                MapFragment.newInstance()
            ) // newInstance() is a static factory method.
            _transaction__.commit()
        }
    }

    fun setNavToolbarTitle(title : String) {
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_nav_home -> {
                val __manager__ = supportFragmentManager
                val _transaction__ = __manager__.beginTransaction()
                _transaction__.replace(
                    R.id.container,
                    MapFragment.newInstance()
                ) // newInstance() is a static factory method.
                _transaction__.commit()
            }
            R.id.nav_your_trips -> {
                // Handle the camera action
                val __manager__ = supportFragmentManager
                val _transaction__ = __manager__.beginTransaction()
                _transaction__.replace(
                    R.id.container,
                    YourTripsFragment.newInstance()
                ) // newInstance() is a static factory method.
                _transaction__.commit()
            }

            R.id.nav_wallet -> {
                val __manager__ = supportFragmentManager
                val _transaction__ = __manager__.beginTransaction()
                _transaction__.replace(
                    R.id.container,
                    WalletFragment.newInstance()
                ) // newInstance() is a static factory method.
                _transaction__.commit()

            }
            R.id.nav_account -> {
                val __manager__ = supportFragmentManager
                val _transaction__ = __manager__.beginTransaction()
                _transaction__.replace(
                    R.id.container,
                    MyAccountFragment.newInstance()
                ) // newInstance() is a static factory method.
                _transaction__.commit()

            }
            R.id.nav_help -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
