package ha.thanh.sidebarsmartapp

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_top_header.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val scaleFactor = 6f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        nav_view.setNavigationItemSelectedListener(this)
        button.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                super.onDrawerSlide(drawerView, slideOffset)
                val m = 1 - (slideOffset / scaleFactor)
                val v = 1 - (slideOffset / scaleFactor) / 2
                val slideX = drawerView.width * slideOffset
                content_view.translationX = slideX * v
                content_view.scaleX = m
                content_view.scaleY = m
                val shape = GradientDrawable()
                val shape2 = GradientDrawable()
                shape.setColor(ContextCompat.getColor(content_view.context, R.color.white))
                shape.cornerRadius = slideOffset * 30
                shape2.setColor(ContextCompat.getColor(content_view.context, R.color.blue_10))
                shape2.cornerRadius = slideOffset * 30
                content_view.background = shape
                topPanel.background = shape2
                content_view.translationZ = 10f
                drawer_layout.setBackgroundColor(ContextCompat.getColor(drawerView.context, R.color.blue_10))
            }

        }
        drawer_layout.setScrimColor(Color.TRANSPARENT)
        drawer_layout.drawerElevation = 0f
        drawer_layout.addDrawerListener(actionBarDrawerToggle)

        val windows = this.window
        windows.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        windows.statusBarColor = ContextCompat.getColor(this, R.color.blue_10)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when (item.itemId) {
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
