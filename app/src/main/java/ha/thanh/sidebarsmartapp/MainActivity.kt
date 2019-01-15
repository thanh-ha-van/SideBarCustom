package ha.thanh.sidebarsmartapp

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ha.thanh.sidebarsmartapp.prgrs.MorphingButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


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
                shape.setColor(ContextCompat.getColor(content_view.context, R.color.white))
                shape.cornerRadius = slideOffset * 30
                content_view.background = shape
            }

        }
        drawer_layout.setScrimColor(Color.TRANSPARENT)
        drawer_layout.drawerElevation = 0f
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        val timeCountInMilliSeconds = 1 * 10 * 1000L
        circular_progress.maxProgress = 1 * 10 * 1000.0
        object : CountDownTimer(timeCountInMilliSeconds, 10) {

            override fun onTick(p0: Long) {

                circular_progress.currentProgress((timeCountInMilliSeconds - p0).toDouble())
            }

            override fun onFinish() {

            }
        }.start()

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
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
