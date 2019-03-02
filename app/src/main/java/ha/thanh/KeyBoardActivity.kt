package ha.thanh

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ha.thanh.sidebarsmartapp.R
import kotlinx.android.synthetic.main.view_loading.*

class KeyBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_loading)
        pw_spinner.startSpinning()
    }

}
