package ha.thanh.sidebarsmartapp

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mShimmerViewContainer: ShimmerFrameLayout? = null
    var mContentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        mContentView = findViewById(R.id.data_view)


        mShimmerViewContainer!!.startShimmer()

        Handler().postDelayed({
            onStopAni()
            mShimmerViewContainer?.visibility = View.GONE
            mContentView?.visibility = View.VISIBLE
        }, 5000)

    }

    override fun onBackPressed() {
    }

    private fun onStopAni() {
        // Stopping Shimmer Effect's animation after data is loaded to ListView
        mShimmerViewContainer!!.stopShimmer()
        mShimmerViewContainer!!.visibility = View.GONE
    }

}
