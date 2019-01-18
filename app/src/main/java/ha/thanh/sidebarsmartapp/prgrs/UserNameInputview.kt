package ha.thanh.sidebarsmartapp.prgrs

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import ha.thanh.sidebarsmartapp.R
import kotlinx.android.synthetic.main.view_pin_input.view.*

class UserNameInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), UserNameInput, View.OnFocusChangeListener {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        View.inflate(context, R.layout.view_pin_input, this)
        tvInputField.setSelection(tvInputField.length())
        tvInputField.onFocusChangeListener = this
        val ta = context.obtainStyledAttributes(attrs, R.styleable.UserNameInputView)
        try {
            val laborText = ta.getString(R.styleable.UserNameInputView_input_labor)
            tvLabor.text = laborText
            val hintText = ta.getString(R.styleable.UserNameInputView_input_hint_message)
            tvHint.text = hintText
        } finally {
            ta.recycle()
        }
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (tvInputField.hasFocus()) {

        } else {

        }
    }

    override fun showError(messageId: Int) {
        tvError.text = context.resources.getString(id)
        if (tvError.visibility != View.VISIBLE) {
            tvError.visibility = View.VISIBLE
        }
    }

    override fun hideError() {
        tvError.visibility = View.INVISIBLE
    }

    override fun showHint(messageId: Int) {
        tvHint.text = context.resources.getString(id)
        if (tvHint.visibility != View.VISIBLE) {
            tvHint.visibility = View.VISIBLE
        }
    }

    override fun hideHint() {
        tvHint.visibility = View.GONE
    }

    override fun doOfflineValidation() {
        //TODO do offline validation rules
        if (tvInputField.text.toString() == "aaa")
            onOfflineValidationFail(R.string.app_name)
        onOfflineValidationSuccess()
    }

    override fun onOfflineValidationFail(messageId: Int) {
        showError(messageId)
    }

    override fun onOfflineValidationSuccess() {
        //TODO call presenter to do online validation process
    }
}