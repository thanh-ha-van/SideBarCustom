package ha.thanh.sidebarsmartapp.prgrs

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ha.thanh.sidebarsmartapp.R
import kotlinx.android.synthetic.main.view_pin_input.view.*

class CustomPinInput @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        View.inflate(context, R.layout.view_pin_input, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomPinInput)
        try {
            val text = ta.getString(R.styleable.CustomPinInput_hintText)
            val color = ta.getColor(R.styleable.CustomPinInput_hintTextColor,
                ContextCompat.getColor(context, R.color.white))
            hintMessage.text = text
            hintMessage.setTextColor(color)
        } finally {
            ta.recycle()
        }
    }
}