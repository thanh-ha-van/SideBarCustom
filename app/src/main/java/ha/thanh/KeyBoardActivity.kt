package ha.thanh

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import ha.thanh.sidebarsmartapp.R
import kotlinx.android.synthetic.main.activity_key_board.*
import kotlinx.android.synthetic.main.view_pin_input.view.*

class KeyBoardActivity : AppCompatActivity() {

    private var status = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_board)

        userName1.tvInputField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                userName1.setStatusType(status)
                status++
                if (status == 4) status = 1
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

}
